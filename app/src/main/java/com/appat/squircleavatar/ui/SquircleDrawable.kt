package com.appat.squircleavatar.ui

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.abs
import kotlin.math.pow

/**
 * Drawable able to draw a squircle picture from a bitmap passed to it.
 *
 * Formula:
 * |x/radius|^n + |y/radius|^n = 1
 *
 * n - default value 4.
 */
class SquircleDrawable(bitmap: Bitmap, degree: Float = 4f) : Drawable() {

    /**
     * @property n Power. Used in squircle formula.
     */
    private val n:Float

    /**
     * @property picture Root bitmap.
     */
    private var picture: Bitmap

    /**
     * @property paintShader
     */
    private val paintShader = Paint().apply{
        isAntiAlias = true
    }

    /**
     * @property bitmapShader used to draw a bitmap as a texture.
     *                        The bitmap can be repeated or mirrored
     *                        by setting the tiling mode.
     */
    private lateinit var bitmapShader: BitmapShader

    /**
     * The path to be created by the squircle formula.
     */
    private lateinit var path: Path

    init {
        if(degree<=0){
            throw IllegalArgumentException("The degree cannot be less than or equal to zero!")
        }
        n = degree
        picture = prepareBitmap(bitmap)
    }

    /**
     * @param raw bitmap
     */
    fun setPictureBitmap(src: Bitmap) {
        picture = prepareBitmap(src)
    }

    /**
     * Method for preparing root bitmap from raw bitmap.
     *
     * @param src raw bitmap
     *
     * @return root bitmap
     */
    private fun prepareBitmap(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height
        val (startPoint, minSide) = if (w > h) {
            val dif = (w - h) / 2
            Point(dif, 0) to h
        } else {
            val dif = (h - w) / 2
            Point(0, dif) to w
        }
        val picture = Bitmap.createBitmap(src, startPoint.x, startPoint.y, minSide, minSide)
        path = createSquirclePath(minSide)
        bitmapShader = BitmapShader(
            picture,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
        paintShader.shader = bitmapShader
        return picture
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(path, paintShader)
    }

    override fun setAlpha(alpha: Int) {
        paintShader.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paintShader.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun getIntrinsicWidth(): Int {
        return picture.width
    }

    override fun getIntrinsicHeight(): Int {
        return picture.height
    }

    /**
     * Mask path creation method.
     *
     * @return mask [Path].
     */
    private fun createSquirclePath(side: Int): Path {
        val path = Path()
        val radius = (side / 2).toFloat()
        path.moveTo(-radius, 0f)
        var x = -radius
        while (x <= radius) {
            path.lineTo(x + radius, calculationY(radius, x) + radius)
            x += 0.5f
        }
        x = radius
        while (x >= -radius) {
            path.lineTo(x + radius, -calculationY(radius, x) + radius)
            x -= 0.5f
        }
        return path
    }

    /**
     * Method for calculating Y from the passed X value and radius.
     *
     * Formula:
     * |x/radius|^n + |y/radius|^n = 1
     *
     * @param radius view.
     *
     * @param x coordinate.
     *
     * @return y coordinate.
     */
    private fun calculationY(radius: Float, x: Float): Float {
        val yN = 1 - (abs(x / radius).pow(n))
        val base = yN * ((radius).pow(n)).toDouble()
        val y = base.pow(1.0 / n.toDouble())
        return y.toFloat()
    }
}