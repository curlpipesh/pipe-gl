package me.curlpipesh.gl.tessellation;

import me.curlpipesh.gl.util.BufferUtil;

/**
 * Using an interface for this allows us to have interchangeable Tessellators,
 * which means that we don't actually have to worry about the implementation
 * details, since those will be "hidden" from us.
 *
 * @author audrey
 * @since 31.08.2014
 */
public interface Tessellator extends BufferUtil {
    /**
     * Starts drawing with the given GL cap for mode. One obvious cap is
     * GL_QUADS
     *
     * @param mode The cap used to set the draw mode
     *
     * @return Itself
     */
    Tessellator startDrawing(int mode);

    /**
     * Sets the color to be applied to all vertices, until the next invocation
     * of this method
     *
     * @param color The new color to be set
     * @return Itself
     */
    Tessellator color(int color);

    /**
     * Adds a vertex at the given x-, y-, and z-coordinates
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return Itself
     */
    Tessellator addVertex(float x, float y, float z);

    /**
     * Adds a vertex at the given x-, y-, and z-coordinates. Intended to just
     * typecast to float.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return Itself
     */
    Tessellator addVertex(double x, double y, double z);

    /**
     * Sets the U- and V-coordinates for the current vertex
     *
     * @param u U-coordinate
     * @param v V-coordinate
     *
     * @return Itself
     */
    Tessellator addUV(double u, double v);

    /**
     * Adds a vertex with the given x-, y-, and z-coordinates with the given
     * U/V coordinates
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param z Z-coordinate
     * @param u U-coordinate
     * @param v V-coordinate
     * @return Itself
     */
    Tessellator addVertexWithUV(double x, double y, double z, double u, double v);

    /**
     * Binds the Tessellator, setting up the various GL calls
     *
     * @return Itself
     */
    Tessellator bind();

    /**
     * Actually renders all vertices
     *
     * @return Itself
     */
    Tessellator draw();

    /**
     * Cleans up all the buffers, resets color, index, etc
     *
     * @return Itself
     */
    Tessellator reset();

    /**
     * Convenience method that should just be
     * <code>
     *     bind().draw().reset();
     * </code>
     *
     * @return Itself
     */
    Tessellator bindAndDraw();
}
