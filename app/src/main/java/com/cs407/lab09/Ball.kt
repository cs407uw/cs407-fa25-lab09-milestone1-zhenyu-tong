package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if (isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        val dt2 = dT * dT

        // Eq (1): v1 = v0 + 1/2 (a1 + a0) * dt
        val newVx = velocityX + 0.5f * (xAcc + accX) * dT
        val newVy = velocityY + 0.5f * (yAcc + accY) * dT

        // Eq (2): l = v0*dt + 1/6 * dt^2 * (3a0 + a1)
        val dx = velocityX * dT + (dt2 / 6f) * (3f * accX + xAcc)
        val dy = velocityY * dT + (dt2 / 6f) * (3f * accY + yAcc)

        posX += dx
        posY += dy

        velocityX = newVx
        velocityY = newVy

        accX = xAcc
        accY = yAcc

        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        val minX = 0f
        val maxX = (backgroundWidth - ballSize).coerceAtLeast(0f)
        val minY = 0f
        val maxY = (backgroundHeight - ballSize).coerceAtLeast(0f)

        // Left wall
        if (posX < minX) {
            posX = minX
            velocityX = 0f
            accX = 0f
        }

        // Right wall
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Top wall
        if (posY < minY) {
            posY = minY
            velocityY = 0f
            accY = 0f
        }

        // Bottom wall
        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f

        velocityX = 0f
        velocityY = 0f

        accX = 0f
        accY = 0f

        isFirstUpdate = true

        checkBoundaries()
    }
}
