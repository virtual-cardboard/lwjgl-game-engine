/*
 * Copyright (c) 2002-2008 LWJGL Project All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package common.math;

import java.io.Serializable;
import java.nio.FloatBuffer;
import java.util.Objects;

/**
 *
 * Holds a 2-tuple vector.
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$ $Id$
 */

public class Vector2f extends Vector implements Serializable, ReadableVector2f, WritableVector2f {

	private static final long serialVersionUID = 1L;

	public static final Vector2f ONE_ONE = new Vector2f(1, 1);

	public float x, y;

	/**
	 * Constructor for Vector2f.
	 */
	public Vector2f() {
		super();
	}

	/**
	 * Constructor.
	 */
	public Vector2f(ReadableVector2f src) {
		set(src);
	}

	/**
	 * Constructor.
	 */
	public Vector2f(float x, float y) {
		set(x, y);
	}

	public static Vector2f fromAngleLength(float angle, float length) {
		return new Vector2f((float) (length * Math.cos(angle)), (float) (length * Math.sin(angle)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lwjgl.util.vector.WritableVector2f#set(float, float)
	 */
	@Override
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Load from another Vector2f
	 * 
	 * @param src The source vector
	 * @return this
	 */
	public Vector2f set(ReadableVector2f src) {
		x = src.getX();
		y = src.getY();
		return this;
	}

	/**
	 * @return the length squared of the vector
	 */
	@Override
	public float lengthSquared() {
		return x * x + y * y;
	}

	@Override
	public Vector2f normalise() {
		float len = length();
		if (len != 0.0f) {
			float l = 1.0f / len;
			return scale(l);
		} else
			throw new IllegalStateException("Zero length vector");
	}

	/**
	 * Translate a vector
	 * 
	 * @param x The translation in x
	 * @param y the translation in y
	 * @return this
	 */
	public Vector2f translate(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Negate a vector
	 * 
	 * @return this
	 */
	@Override
	public Vector2f negate() {
		x = -x;
		y = -y;
		return this;
	}

	/**
	 * Negate a vector and place the result in a destination vector.
	 * 
	 * @param dest The destination vector or null if a new vector is to be created
	 * @return the negated vector
	 */
	public Vector2f negate(Vector2f dest) {
		if (dest == null)
			dest = new Vector2f();
		dest.x = -x;
		dest.y = -y;
		return dest;
	}

	/**
	 * Normalise this vector and place the result in another vector.
	 * 
	 * @param dest The destination vector, or null if a new vector is to be created
	 * @return the normalised vector
	 */
	public Vector2f normalise(Vector2f dest) {
		float l = length();

		if (dest == null)
			dest = new Vector2f(x / l, y / l);
		else
			dest.set(x / l, y / l);

		return dest;
	}

	public float dot(Vector2f vector2f) {
		return x * vector2f.x + y * vector2f.y;
	}

	/**
	 * The dot product of two vectors is calculated as v1.x * v2.x + v1.y * v2.y +
	 * v1.z * v2.z
	 * 
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @return left dot right
	 */
	public static float dot(Vector2f left, Vector2f right) {
		return left.x * right.x + left.y * right.y;
	}

	public Vector2f projectOnto(Vector2f vector) {
		return set(vector.copy().scale(dot(vector) / vector.lengthSquared()));
	}

	public Vector2f reflect(Vector2f vector) {
		return set(projectOnto(vector).scale(2).sub(this));
	}

	/**
	 * Calculate the angle between two vectors, in radians
	 * 
	 * @param a A vector
	 * @param b The other vector
	 * @return the angle between the two vectors, in radians
	 */
	public static float angle(Vector2f a, Vector2f b) {
		float dls = dot(a, b) / (a.length() * b.length());
		if (dls < -1f)
			dls = -1f;
		else if (dls > 1.0f)
			dls = 1.0f;
		return (float) Math.acos(dls);
	}

	/**
	 * Rotates the vector around the origin, counterclockwise.
	 * 
	 * @param radians the angle to rotate the vector by, in radians.
	 * @return the vector itself
	 */
	public Vector2f rotate(float radians) {
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);
		double newX = x * cos - y * sin;
		double newY = x * sin + y * cos;
		x = (float) (newX);
		y = (float) (newY);
		return this;
	}

	public Vector2f add(Vector2f v) {
		x = v.x + x;
		y = v.y + y;
		return this;
	}

	public Vector2f add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Add a vector to another vector and place the result in a destination vector.
	 * 
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @param dest  The destination vector, or null if a new vector is to be created
	 * @return the sum of left and right in dest
	 */
	public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
		if (dest == null)
			return add(left, right);
		else {
			dest.set(add(left, right));
			return dest;
		}
	}

	/**
	 * Add a vector to another vector return the reult.
	 * 
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @return the sum of left and right
	 */
	public static Vector2f add(Vector2f left, Vector2f right) {
		return new Vector2f(left.x + right.x, left.y + right.y);
	}

	public Vector2f sub(Vector2f v) {
		x = x - v.x;
		y = y - v.y;
		return this;
	}

	/**
	 * Subtract a vector from another vector and place the result in a destination
	 * vector.
	 * 
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @param dest  The destination vector, or null if a new vector is to be created
	 * @return left minus right in dest
	 */
	public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
		if (dest == null)
			return sub(left, right);
		else {
			dest.set(sub(left, right));
			return dest;
		}
	}

	/**
	 * Subtract a vector from another vector and return the result.
	 * 
	 * @param left  The LHS vector
	 * @param right The RHS vector
	 * @return the resulting vector
	 */
	public static Vector2f sub(Vector2f left, Vector2f right) {
		return new Vector2f(left.x - right.x, left.y - right.y);
	}

	/**
	 * Store this vector in a FloatBuffer
	 * 
	 * @param buf The buffer to store it in, at the current position
	 * @return this
	 */
	@Override
	public Vector2f store(FloatBuffer buf) {
		buf.put(x);
		buf.put(y);
		return this;
	}

	/**
	 * Load this vector from a FloatBuffer
	 * 
	 * @param buf The buffer to load it from, at the current position
	 * @return this
	 */
	@Override
	public Vector2f load(FloatBuffer buf) {
		x = buf.get();
		y = buf.get();
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lwjgl.vector.Vector#scale(float)
	 */
	@Override
	public Vector2f scale(float scale) {
		x *= scale;
		y *= scale;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(64);

		sb.append("Vector2f[");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(']');
		return sb.toString();
	}

	/**
	 * @return x
	 */
	@Override
	public final float getX() {
		return x;
	}

	/**
	 * @return y
	 */
	@Override
	public final float getY() {
		return y;
	}

	/**
	 * Set X
	 * 
	 * @param x
	 */
	@Override
	public final void setX(float x) {
		this.x = x;
	}

	/**
	 * Set Y
	 * 
	 * @param y
	 */
	@Override
	public final void setY(float y) {
		this.y = y;
	}

	public Vector2f round(int decimalPoints) {
		x = (float) (Math.round((float) (x * Math.pow(10, decimalPoints))) / Math.pow(10, decimalPoints));
		y = (float) (Math.round((float) (y * Math.pow(10, decimalPoints))) / Math.pow(10, decimalPoints));
		return this;
	}

	@Override
	public Vector2f copy() {
		Vector2f v = new Vector2f(x, y);
		return v;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vector2f other = (Vector2f) obj;
		if (x == other.x && y == other.y)
			return true;
		return false;
	}

	public float getAngle() {
		return (float) ((Math.atan2(y, x) + 2 * Math.PI) % (2 * Math.PI));
	}

}
