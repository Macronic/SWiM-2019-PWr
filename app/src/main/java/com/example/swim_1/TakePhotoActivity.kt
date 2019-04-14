package com.example.swim_1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.hardware.camera2.CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.view.Surface
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.swim_1.AddDogActivity.Companion.photoPathDataName
import kotlinx.android.synthetic.main.activity_take_photo.*
import java.io.File
import java.io.FileOutputStream
import java.util.*




class TakePhotoActivity : AppCompatActivity() {

    var cameraManager : CameraManager? = null
    var usedCameraId = ""
    var camera : CameraDevice? = null
    var cameraCaptureSessions: CameraCaptureSession? = null
    var captureRequestBuilder: CaptureRequest.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)
        backButton.setOnClickListener { finish() }
        internalSaveButton.setOnClickListener { internalSave() }
        externalSaveButton.setOnClickListener { externalSave() }
    }

    private val cameraStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice?) {
            this@TakePhotoActivity.camera = camera
            createCameraPreview()
        }
        override fun onClosed(camera: CameraDevice?) {
            this@TakePhotoActivity.camera = null
        }
        override fun onDisconnected(camera: CameraDevice?) {
            this@TakePhotoActivity.camera = null
        }
        override fun onError(camera: CameraDevice?, error: Int) {
            this@TakePhotoActivity.camera = null
        }
    }

    protected fun createCameraPreview() {
        try {
            val texture = cameraView.surfaceTexture!!
            texture.setDefaultBufferSize(600, 800)
            val surface = Surface(texture)
            captureRequestBuilder = camera?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder?.addTarget(surface)
            camera?.createCaptureSession(Arrays.asList(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                    camera ?: return
                    cameraCaptureSessions = cameraCaptureSession
                    updatePreview()
                }

                override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) { }
            }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {

            val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            this@TakePhotoActivity.cameraManager = cameraManager
            val cameras = cameraManager.cameraIdList
            for (cameraId in cameras) {
                if (cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                    usedCameraId = cameraId
                    break
                }
            }

            if (ActivityCompat.checkSelfPermission(this@TakePhotoActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this@TakePhotoActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@TakePhotoActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
            }

            if (ContextCompat.checkSelfPermission(this@TakePhotoActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(usedCameraId, cameraStateCallback, null)
            }

        }
        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) { }
        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture) = true
        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) = Unit
    }

    protected fun updatePreview() {
        camera ?: return
        val captureRequestBuilder = this.captureRequestBuilder
        captureRequestBuilder ?: return
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions?.setRepeatingRequest(captureRequestBuilder.build(), null, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun takePicture(file : File) {
        val camera = this.camera
        camera ?: return
        val cameraManager = this.cameraManager
        cameraManager ?: return

        val cameraSettings = cameraManager.getCameraCharacteristics(usedCameraId)
        var width = 640
        var height = 480

        val availableSizes = cameraSettings.get(SCALER_STREAM_CONFIGURATION_MAP)?.getOutputSizes(ImageFormat.JPEG);
        if (availableSizes != null && availableSizes.isNotEmpty()) {
            width = availableSizes[0].width
            height = availableSizes[0].height
        }
        val reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1)
        val outputSurfaces = ArrayList<Surface>(2)
        outputSurfaces.add(reader.surface)
        outputSurfaces.add(Surface(cameraView.surfaceTexture))
        val captureBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        captureBuilder.addTarget(reader.surface)
        captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, (windowManager.defaultDisplay.rotation + 90) % 360)
        reader.setOnImageAvailableListener( {
            var image: Image? = null
            try {
                image = reader.acquireLatestImage()
                val buffer = image!!.planes[0].buffer
                val bytes = ByteArray(buffer.capacity())
                buffer.get(bytes)
                val output = FileOutputStream(file)
                output.use { output -> output.write(bytes) }
                Toast.makeText(this@TakePhotoActivity, "Saved picture, " + file.absolutePath, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@TakePhotoActivity, "Some bug encountered, " + e.message, Toast.LENGTH_LONG).show()
            } finally {
                image?.close()
            }
        }, null)

        val captureCompletedListener =  object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                val intent = Intent()
                intent.putExtra(photoPathDataName, file.absolutePath)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        camera.createCaptureSession(outputSurfaces, object : CameraCaptureSession.StateCallback() {
            override fun onConfigureFailed(session : CameraCaptureSession) {}

            override fun onConfigured(session : CameraCaptureSession) {
                try {
                    session.capture(captureBuilder.build(), captureCompletedListener, null)
                } catch (e : CameraAccessException) {
                    e.printStackTrace()
                }
            }
        }, null)
    }


    fun getFilename() : String {
        val dateFormat = android.text.format.DateFormat.getDateFormat(applicationContext)
        return "dog_" + dateFormat.format(Date()) + ".jpg"
    }

    fun internalSave() {
        takePicture(File(filesDir, getFilename()))
    }


    fun externalSave() {
        takePicture(File(getExternalStorageDirectory(), getFilename()))
    }

    override fun onResume() {
        super.onResume()
        cameraView.surfaceTextureListener = surfaceTextureListener
    }

}
