package engine.graphics;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Vector;
import engine.math.Vector4;
import engine.threed.Camera;
import engine.threed.Mesh;
import engine.threed.Triangle;
import engine.threed.Vertex;
import engine.utils.ArrayUtils;
import engine.utils.MathUtils;

import static engine.utils.MathUtils.*;

public final class ImageAlgorithms3D 
{
    private ImageAlgorithms3D() { }
    
    static void line(final DrawableImage image, 
        final Vertex a, final Vertex b, final Image texture)
    {
        final double dxd = Math.abs(b.x - a.x);
        final double dyd = b.y == a.y ? 1 : Math.abs(b.y - a.y);

        final int dx = (int) Math.abs((int)b.x - (int)a.x);
        final int dy = (int) Math.abs((int)b.y - (int)a.y);

        final int sx = ((int) a.x) < ((int) b.x) ? 1 : -1;
        final int sy = ((int) a.y) < ((int) b.y) ? 1 : -1;

        int err = dx - dy;
        int e2 = 0;

        int x = (int) a.x;
        int y = (int) a.y;

        double xd = a.x;
        double yd = a.y;
        double z = a.z;

        double u = a.texture.x;
        double v = a.texture.y;
        double w = a.texture.z;

        double dl = Math.sqrt(dxd * dxd + dyd * dyd);

        while (true)
        {
            image.drawPixel(x, y, z, texture.getPixel(u, v, w));
            
            double dp = Math.sqrt((xd - x) * (xd - x) + (yd - y) * (yd - y));

            double t = dp / dl;

            if (x == (int) b.x && y == (int) b.y) break;

            e2 = 2 * err;

            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 <  dx) { err += dx; y += sy; }

            z = MathUtils.lerp(a.z, b.z, t);

            u = MathUtils.lerp(a.texture.x, b.texture.x, t);
            v = MathUtils.lerp(a.texture.y, b.texture.y, t);
            w = MathUtils.lerp(a.texture.z, b.texture.z, t);
        }
    }

    static void triangleUnchecked(final DrawableImage image,
        final Triangle triangle, final Image texture)
    {
        Vertex a = triangle.a; Vertex b = triangle.b; Vertex c = triangle.c;

        if      (b.y == c.y) fillBottomFlatTriangle(image, a, b, c, texture);
        else if (a.y == b.y) fillTopFlatTriangle   (image, a, b, c, texture);
        else                 fillNoneFlatTriangle  (image, a, b, c, texture);
    }

    static void triangle(final DrawableImage image, 
        final Triangle triangle, final Image texture)
    {
        triangleUnchecked(image, triangle.sortedForY(), texture);
    }

    static void fillNoneFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        final double v_slope = (b.y - a.y) / (c.y - a.y);

        final double v_dcax = (c.x - a.x);
        final double v_dcay = (c.y - a.y);

        final double x = a.x + (v_slope * v_dcax);
        final double y = b.y;

        final double dl = Math.sqrt(v_dcax * v_dcax + v_dcay * v_dcay);
        final double dp = Math.sqrt((x - a.x) * (x - a.x) + (y - a.y) * (y - a.y));

        final double t = dp / dl;

        Vertex d = new Vertex
        (
            new FinalVector(
                a.x + (v_slope * v_dcax),
                b.y,
                MathUtils.lerp(a.z, c.z, t),
                MathUtils.lerp(a.w, c.w, t)
            ),
            new FinalVector
            (
                MathUtils.lerp(a.texture.x, c.texture.x, t),
                MathUtils.lerp(a.texture.y, c.texture.y, t),
                MathUtils.lerp(a.texture.z, c.texture.z, t)
            ),
            new FinalVector
            (
                MathUtils.lerp(a.normal.x, c.normal.x, t),
                MathUtils.lerp(a.normal.y, c.normal.y, t),
                MathUtils.lerp(a.normal.z, c.normal.z, t),
                MathUtils.lerp(a.normal.w, c.normal.w, t)
            )
        );

        fillBottomFlatTriangle(image, a, b, d, texture);
        fillTopFlatTriangle   (image, b, d, c, texture);
    }

    static void fillBottomFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double invslope1 = (b.x - a.x) / (b.y - a.y);
        double invslope2 = (c.x - a.x) / (c.y - a.y);

        double curx1 = a.x;
        double curx2 = a.x;

        final double dl = Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));

        for (int scanlineY = (int) a.y; scanlineY <= b.y; scanlineY++)
        {
            final double dp = Math.sqrt((curx1 - a.x) * (curx1 - a.x) + (scanlineY - a.y) * (scanlineY - a.y));

            final double t = dp / dl;
            
            final Vertex v1 = new Vertex
            (
                new FinalVector(
                    curx1,
                    scanlineY,
                    MathUtils.lerp(a.z, b.z, t),
                    MathUtils.lerp(a.w, b.w, t)
                ),
                Vector.lerp(a.texture, b.texture, t),
                Vector.lerp(a.normal, b.normal, t)
            );

            final Vertex v2 = new Vertex
            (
                new FinalVector(
                    curx2,
                    scanlineY,
                    MathUtils.lerp(a.z, c.z, t),
                    MathUtils.lerp(a.w, c.w, t)
                ),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal, c.normal, t)
            );

            line(image, v1, v2, texture);

            curx1 += invslope1;
            curx2 += invslope2;
        }
    }

    static void fillTopFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double invslope1 = (c.x - a.x) / (c.y - a.y);
        double invslope2 = (c.x - b.x) / (c.y - b.y);

        double curx1 = c.x;
        double curx2 = c.x;

        final double dl = Math.sqrt((c.x - a.x) * (c.x - a.x) + (c.y - a.y) * (c.y - a.y));

        for (int scanlineY = (int) c.y; scanlineY > a.y; scanlineY--)
        {
            final double dp = Math.sqrt((curx1 - a.x) * (curx1 - a.x) + (scanlineY - a.y) * (scanlineY - a.y));
            final double t = dp / dl;

            final Vertex v1 = new Vertex
            (
                new FinalVector(
                    curx1,
                    scanlineY,
                    MathUtils.lerp(a.z, c.z, t),
                    MathUtils.lerp(a.w, c.w, t)
                ),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal , c.normal, t)
            );

            final Vertex v2 = new Vertex
            (
                new FinalVector(
                    curx2,
                    scanlineY,
                    MathUtils.lerp(b.z, c.z, t),
                    MathUtils.lerp(b.w, c.w, t)
                ),
                Vector.lerp(b.texture, c.texture, t),
                Vector.lerp(b.normal , c.normal, t)
            );

            line(image, v1, v2, texture);
            
            curx1 -= invslope1;
            curx2 -= invslope2;
        }
    }

    static Vector4 intersectPlane(Vector4 planePosition, Vector4 planeNormal, Vector4 lineStart, Vector4 lineEnd) 
    { 
        planeNormal = planeNormal.normalized(); 
        double planeD = -planeNormal.dot(planePosition); 
        double ad = lineStart.dot(planeNormal); 
        double bd = lineEnd.dot(planeNormal); 
        double t = (-planeD - ad) / (bd - ad); 
        Vector4 lineStartToEnd = lineEnd.minus(lineStart); 
        Vector4 lineToIntersect = lineStartToEnd.times(t); 
        return lineStart.plus(lineToIntersect); 
    }

    static double dist(Vector4 p, Vector4 planePosition, Vector4 planeNormal) 
    { 
        return planeNormal.dot(p.minus(planePosition)); 
    }
    
    static Triangle[] clipAgainstPlane(Vector4 planePosition, Vector4 planeNormal, Triangle triangle) 
    { 
        Triangle[] result = new Triangle[0];

        // Make sure plane normal is indeed normal
		planeNormal = planeNormal.normalized();

		// Create two temporary storage arrays to classify points either side of plane
		// If distance sign is positive, point lies on "inside" of plane
		Vertex[] inside_points  = new Vertex[3]; int nInsidePointCount = 0;
		Vertex[] outside_points = new Vertex[3]; int nOutsidePointCount = 0;

		// Get signed distance of each point in triangle to plane
		double d0 = dist(triangle.a, planePosition, planeNormal);
		double d1 = dist(triangle.b, planePosition, planeNormal);
		double d2 = dist(triangle.c, planePosition, planeNormal);

		if (d0 >= 0) { inside_points[nInsidePointCount++] = triangle.a; }
		else {
			outside_points[nOutsidePointCount++] = triangle.a;
		}
		if (d1 >= 0) {
			inside_points[nInsidePointCount++] = triangle.b; 
		}
		else {
			outside_points[nOutsidePointCount++] = triangle.b;
		}
		if (d2 >= 0) {
			inside_points[nInsidePointCount++] = triangle.c;
		}
		else {
			outside_points[nOutsidePointCount++] = triangle.c;
		}

		// Now classify triangle points, and break the input triangle into 
		// smaller output triangles if required. There are four possible
		// outcomes...

		if (nInsidePointCount == 0)
		{
			// All points lie on the outside of plane, so clip whole triangle
			// It ceases to exist

			return result; // No returned triangles are valid
		}

		if (nInsidePointCount == 3)
		{
			// All points lie on the inside of plane, so do nothing
			// and allow the triangle to simply pass through

            result = ArrayUtils.push(result, triangle);

			return result; // Just the one returned original triangle is valid
		}

		if (nInsidePointCount == 1 && nOutsidePointCount == 2)
		{
			// Triangle should be clipped. As two points lie outside
			// the plane, the triangle simply becomes a smaller triangle

            double t = (planePosition.minus(inside_points[0])).dot(planeNormal) / (outside_points[0].minus(inside_points[0])).dot(planeNormal);

            Triangle newTriangle = new Triangle(
                inside_points[0],
                new Vertex(
                    intersectPlane(planePosition, planeNormal, inside_points[0], outside_points[0]),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t)
                ),
                new Vertex(
                    intersectPlane(planePosition, planeNormal, inside_points[0], outside_points[0]),
                    Vector.lerp(inside_points[0].texture, outside_points[1].texture, t),
                    Vector.lerp(inside_points[0].texture, outside_points[1].texture, t)
                )
            );

            result = ArrayUtils.push(result, newTriangle);

			// Copy appearance info to new triangle
			//out_tri1.col =  in_tri.col;
			//out_tri1.sym = in_tri.sym;
//
			//// The inside point is valid, so keep that...
			//out_tri1.p[0] = *inside_points[0];
			//out_tri1.t[0] = *inside_tex[0];
//
			//// but the two new points are at the locations where the 
			//// original sides of the triangle (lines) intersect with the plane
			//float t;
			//out_tri1.p[1] = Vector_IntersectPlane(plane_p, plane_n, *inside_points[0], *outside_points[0], t);
			//out_tri1.t[1].u = t * (outside_tex[0]->u - inside_tex[0]->u) + inside_tex[0]->u;
			//out_tri1.t[1].v = t * (outside_tex[0]->v - inside_tex[0]->v) + inside_tex[0]->v;
			//out_tri1.t[1].w = t * (outside_tex[0]->w - inside_tex[0]->w) + inside_tex[0]->w;
//
			//out_tri1.p[2] = Vector_IntersectPlane(plane_p, plane_n, *inside_points[0], *outside_points[1], t);
			//out_tri1.t[2].u = t * (outside_tex[1]->u - inside_tex[0]->u) + inside_tex[0]->u;
			//out_tri1.t[2].v = t * (outside_tex[1]->v - inside_tex[0]->v) + inside_tex[0]->v;
			//out_tri1.t[2].w = t * (outside_tex[1]->w - inside_tex[0]->w) + inside_tex[0]->w;

			return result; // Return the newly formed single triangle
		}

		if (nInsidePointCount == 2 && nOutsidePointCount == 1)
		{
			// Triangle should be clipped. As two points lie inside the plane,
			// the clipped triangle becomes a "quad". Fortunately, we can
			// represent a quad with two new triangles

            double t = (planePosition.minus(inside_points[0])).dot(planeNormal) / (outside_points[0].minus(inside_points[0])).dot(planeNormal);

            Triangle a = new Triangle(
                inside_points[0],
                inside_points[1],
                new Vertex(
                    intersectPlane(planePosition, planeNormal, inside_points[0], outside_points[0]),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t)
                )
            );

            Triangle b = new Triangle(
                inside_points[1],
                new Vertex(
                    intersectPlane(planePosition, planeNormal, inside_points[0], outside_points[0]),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t)
                ),
                new Vertex(
                    intersectPlane(planePosition, planeNormal, inside_points[1], outside_points[0]),
                    Vector.lerp(inside_points[1].texture, outside_points[0].texture, t),
                    Vector.lerp(inside_points[1].texture, outside_points[0].texture, t)
                )
            );

            result = ArrayUtils.push(result, a, b);

			// Copy appearance info to new triangles
			//out_tri1.col =  in_tri.col;
			//out_tri1.sym = in_tri.sym;
//
			//out_tri2.col =  in_tri.col;
			//out_tri2.sym = in_tri.sym;
//
			//// The first triangle consists of the two inside points and a new
			//// point determined by the location where one side of the triangle
			//// intersects with the plane
			//out_tri1.p[0] = *inside_points[0];
			//out_tri1.p[1] = *inside_points[1];
			//out_tri1.t[0] = *inside_tex[0];
			//out_tri1.t[1] = *inside_tex[1];
//
			//float t;
			//out_tri1.p[2] = Vector_IntersectPlane(plane_p, plane_n, *inside_points[0], *outside_points[0], t);
			//out_tri1.t[2].u = t * (outside_tex[0]->u - inside_tex[0]->u) + inside_tex[0]->u;
			//out_tri1.t[2].v = t * (outside_tex[0]->v - inside_tex[0]->v) + inside_tex[0]->v;
			//out_tri1.t[2].w = t * (outside_tex[0]->w - inside_tex[0]->w) + inside_tex[0]->w;
//
			//// The second triangle is composed of one of he inside points, a
			//// new point determined by the intersection of the other side of the 
			//// triangle and the plane, and the newly created point above
			//out_tri2.p[0] = *inside_points[1];
			//out_tri2.t[0] = *inside_tex[1];
			//out_tri2.p[1] = out_tri1.p[2];
			//out_tri2.t[1] = out_tri1.t[2];
			//out_tri2.p[2] = Vector_IntersectPlane(plane_p, plane_n, *inside_points[1], *outside_points[0], t);
			//out_tri2.t[2].u = t * (outside_tex[0]->u - inside_tex[1]->u) + inside_tex[1]->u;
			//out_tri2.t[2].v = t * (outside_tex[0]->v - inside_tex[1]->v) + inside_tex[1]->v;
			//out_tri2.t[2].w = t * (outside_tex[0]->w - inside_tex[1]->w) + inside_tex[1]->w;
			return result; // Return two newly formed triangles which form a quad
		}

        return result;
    }

    public static void mesh(final DrawableImage image, final Mesh mesh, final Matrix transform, final Matrix projection, final Matrix view, final Vector4 cameraPosition, final Image texture)
    {
        Triangle[] triangles = new Triangle[0];

        for (int i = 0; i < mesh.triangles.length; i++)
        {
            final Triangle triangle = mesh.triangles[i];

            Triangle transformedTriangle = triangle.transformed(transform);

            Vector4 line1 = transformedTriangle.b.minus(transformedTriangle.a);
            Vector4 line2 = transformedTriangle.c.minus(transformedTriangle.a);

            Vector4 normal = line1.cross(line2).normalized();

            Vector4 cameraRay = transformedTriangle.a.minus(cameraPosition);

            if (normal.dot(cameraRay) < 0.0)
            {
                Triangle viewTriangle = transformedTriangle.transformed(view);

                Triangle[] clippedTriangles = clipAgainstPlane(vec(0, 0, 0.1), vec(0, 0, 1), viewTriangle);

                for (int j = 0; j < clippedTriangles.length; j++)
                {
                    Triangle projectedTriangle = clippedTriangles[j].transformed(projection);

                    final Vector4 offsetView = vec(1, 1);

                    //actully simple but brutalley messy code for now, change later to something way more readable
                    
                   //System.out.println(
                   //    projection
                   //);

                    Vertex a = new Vertex
                    (
                        projectedTriangle.a.dividedBy(projectedTriangle.a.w).times(-1, -1).add(offsetView).times(0.5 * image.width(), 0.5 * image.height()),
                        new FinalVector
                        (
                            projectedTriangle.a.texture.x / projectedTriangle.a.w,
                            projectedTriangle.a.texture.y / projectedTriangle.a.w,
                            1 / projectedTriangle.a.w
                        ),
                        projectedTriangle.a.normal
                    );

                    Vertex b = new Vertex
                    (
                        projectedTriangle.b.dividedBy(projectedTriangle.b.w).times(-1, -1).add(offsetView).times(0.5 * image.width(), 0.5 * image.height()),
                        new FinalVector
                        (
                            projectedTriangle.b.texture.x / projectedTriangle.b.w,
                            projectedTriangle.b.texture.y / projectedTriangle.b.w,
                            1 / projectedTriangle.b.w
                        ),
                        projectedTriangle.b.normal
                    );

                    Vertex c = new Vertex
                    (
                        projectedTriangle.c.dividedBy(projectedTriangle.c.w).times(-1, -1).add(offsetView).times(0.5 * image.width(), 0.5 * image.height()),
                        new FinalVector
                        (
                            projectedTriangle.c.texture.x / projectedTriangle.c.w,
                            projectedTriangle.c.texture.y / projectedTriangle.c.w,
                            1 / projectedTriangle.c.w
                        ),
                        projectedTriangle.c.normal
                    );

                    triangles = ArrayUtils.push(triangles, new Triangle(a, b, c));
                }
            }
        }

        final Vector4[] clipPlanes = new Vector4[]
        {
            vec(0, 0, 0),
            vec(0, image.height() - 1, 1000),
            vec(0, 0, 0),
            vec(image.width() - 1, 0, 1)
        };

        final Vector4[] clipNormals = new Vector4[]
        {
            vec(0, 1, 0),
            vec(0, -1, 0),
            vec(1, 0, 0),
            vec(-1, 0, 0)
        };

        Triangle[] clippedTriangles = new Triangle[0];

        for (int i = 0; i < triangles.length; i++)
        {
            Triangle triangle = triangles[i];

            Triangle[] clipped = new Triangle[] { triangle };

            for (int j = 0; j < clipPlanes.length; j++)
            {
                Triangle[] newTriangles = new Triangle[0];

                for (int k = 0; k < clipped.length; k++)
                {
                    Triangle[] clippedTriangle = clipAgainstPlane(clipPlanes[j], clipNormals[j], clipped[k]);

                    newTriangles = ArrayUtils.push(newTriangles, clippedTriangle);
                }

                clipped = newTriangles;
            }

            clippedTriangles = ArrayUtils.push(clippedTriangles, clipped);
        }

        for (int i = 0; i < triangles.length; i++)
        {
            //System.out.println(triangles[i]);

            triangle(image, triangles[i], texture);
        }
    }
}
