package engine.graphics;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Vector;
import engine.math.Vector4;
import engine.threed.Mesh;
import engine.threed.Triangle;
import engine.threed.Vertex;
import engine.utils.ArrayUtils;

import static engine.utils.MathUtils.*;

final class ImageAlgorithms3D 
{
    ImageAlgorithms3D() { }
    
    static void line(final DrawableImage image, 
        final Vertex a, final Vertex b, final Image texture)
    {
        double dxd = abs(b.x - a.x)+1;
        double dyd = abs(b.y - a.y);
        
        int dx = (int) abs((int) b.x - (int) a.x);
        int dy = (int) abs((int) b.y - (int) a.y);

        double longerSideLength = dxd > dyd ? dxd : dyd;
        double inversedLongerSideLength = 1 / longerSideLength;

        final int sx = a.x < b.x ? 1 : -1;
        final int sy = a.y < b.y ? 1 : -1;

        int err = dx - dy;
        int e2 = 0;

        int x = (int) a.x;
        int y = (int) a.y;

        double z = a.z;

        double u = a.texture.x;
        double v = a.texture.y;
        double w = a.texture.z;

        double z_slope = (b.z - a.z) * inversedLongerSideLength;

        double u_slope = (b.texture.x - a.texture.x) * inversedLongerSideLength;
        double v_slope = (b.texture.y - a.texture.y) * inversedLongerSideLength;
        double w_slope = (b.texture.z - a.texture.z) * inversedLongerSideLength;

        while (true)
        {
            image.drawPixel(x, y, z, texture.getPixel(u, v, w));

            if (x == (int)b.x && y == (int)b.y) break;

            e2 = 2 * err;

            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 <  dx) { err += dx; y += sy; }

            z += z_slope;

            u += u_slope;
            v += v_slope;
            w += w_slope;
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
        //System.out.println(triangle);

        triangleUnchecked(image, triangle.sortedForY(), texture);
    }

    static void fillNoneFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        final double slope = (b.y - a.y) / (c.y - a.y);

        final double dcax = (c.x - a.x);
        final double dcay = (c.y - a.y);

        final double x = a.x + (slope * dcax);
        final double y = b.y;

        final double dl = sqrt(dcax * dcax + dcay * dcay);
        final double dp = sqrt((x - a.x) * (x - a.x) + (y - a.y) * (y - a.y));

        final double t = dp / dl;

        Vertex d = new Vertex
        (
            fvec(
                a.x + (slope * dcax),
                b.y,
                lerp(a.z, c.z, t),
                lerp(a.w, c.w, t)
            ),
            lerp(a.texture, c.texture, t),
            lerp(a.normal , c.normal , t)
        );

        fillBottomFlatTriangle(image, a, b, d, texture);
        fillTopFlatTriangle   (image, b, d, c, texture);
    }

    static void fillBottomFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double ay = floor(a.y);
        double by = floor(b.y);
        double cy = floor(c.y);

        double x1 = a.x;
        double x2 = a.x;

        double z1 = a.z;
        double z2 = a.z;

        double w1 = a.w;
        double w2 = a.w;

        double u1 = a.texture.x;
        double v1 = a.texture.y;
        double tw1 = a.texture.z;

        double u2 = a.texture.x;
        double v2 = a.texture.y;
        double tw2 = a.texture.z;

        double nx1 = a.normal.x;
        double ny1 = a.normal.y;
        double nz1 = a.normal.z;

        double nx2 = a.normal.x;
        double ny2 = a.normal.y;
        double nz2 = a.normal.z;

        double invdy1 = 1 / (by - ay);
        double invdy2 = 1 / (cy - ay);

        double x1_slope = (b.x - a.x) * invdy1;
        double z1_slope = (b.z - a.z) * invdy1;
        double w1_slope = (b.w - a.w) * invdy1;

        double u1_slope = (b.texture.x - a.texture.x) * invdy1;
        double v1_slope = (b.texture.y - a.texture.y) * invdy1;
        double tw1_slope = (b.texture.z - a.texture.z) * invdy1;

        double nx1_slope = (b.normal.x - a.normal.x) * invdy1;
        double ny1_slope = (b.normal.y - a.normal.y) * invdy1;
        double nz1_slope = (b.normal.z - a.normal.z) * invdy1;

        double x2_slope = (c.x - a.x) * invdy2;
        double z2_slope = (c.z - a.z) * invdy2;
        double w2_slope = (c.w - a.w) * invdy2;

        double u2_slope = (c.texture.x - a.texture.x) * invdy2;
        double v2_slope = (c.texture.y - a.texture.y) * invdy2;
        double tw2_slope = (c.texture.z - a.texture.z) * invdy2;

        double nx2_slope = (c.normal.x - a.normal.x) * invdy2;
        double ny2_slope = (c.normal.y - a.normal.y) * invdy2;
        double nz2_slope = (c.normal.z - a.normal.z) * invdy2;

        for (int scanlineY = (int) ay; scanlineY <= by; scanlineY++)
        {
            final Vertex vertex1 = new Vertex
            (
                fvec((int)x1, scanlineY, z1, w1),
                fvec(u1, v1, tw1),
                fvec(nx1, ny1, nz1)
            );

            final Vertex vertex2 = new Vertex
            (
                fvec((int)x2, scanlineY, z2, w2),
                fvec(u2, v2, tw2),
                fvec(nx2, ny2, nz2)
            );

            line(image, vertex1, vertex2, texture);

            x1 += x1_slope;
            z1 += z1_slope;
            w1 += w1_slope;

            u1  += u1_slope;
            v1  += v1_slope;
            tw1 += tw1_slope;
            
            nx1 += nx1_slope;
            ny1 += ny1_slope;
            nz1 += nz1_slope;

            x2 += x2_slope;
            z2 += z2_slope;
            w2 += w2_slope;

            u2  += u2_slope;
            v2  += v2_slope;
            tw2 += tw2_slope;
            
            nx2 += nx2_slope;
            ny2 += ny2_slope;
            nz2 += nz2_slope;
        }
    }

    static void fillTopFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double ay = floor(a.y);
        double by = floor(b.y);
        double cy = floor(c.y);

        double x1 = c.x;
        double x2 = c.x;

        double z1 = c.z;
        double z2 = c.z;

        double w1 = c.w;
        double w2 = c.w;

        double u1 = c.texture.x;
        double v1 = c.texture.y;
        double tw1 = c.texture.z;

        double u2 = c.texture.x;
        double v2 = c.texture.y;
        double tw2 = c.texture.z;

        double nx1 = c.normal.x;
        double ny1 = c.normal.y;
        double nz1 = c.normal.z;

        double nx2 = c.normal.x;
        double ny2 = c.normal.y;
        double nz2 = c.normal.z;

        double invdy1 = 1.0 / (cy - ay);
        double invdy2 = 1.0 / (cy - by);

        double x1_slope = (c.x - a.x) * invdy1;
        double z1_slope = (c.z - a.z) * invdy1;
        double w1_slope = (c.w - a.w) * invdy1;
        
        double u1_slope  = (c.texture.x - a.texture.x) * invdy1;
        double v1_slope  = (c.texture.y - a.texture.y) * invdy1;
        double tw1_slope = (c.texture.z - a.texture.z) * invdy1;

        double nx1_slope = (c.normal.x - a.normal.x) * invdy1;
        double ny1_slope = (c.normal.y - a.normal.y) * invdy1;
        double nz1_slope = (c.normal.z - a.normal.z) * invdy1;

        double x2_slope = (c.x - b.x) * invdy2;
        double z2_slope = (c.z - b.z) * invdy2;
        double w2_slope = (c.w - b.w) * invdy2;

        double u2_slope  = (c.texture.x - b.texture.x) * invdy2;
        double v2_slope  = (c.texture.y - b.texture.y) * invdy2;
        double tw2_slope = (c.texture.z - b.texture.z) * invdy2;

        double nx2_slope = (c.normal.x - b.normal.x) * invdy2;
        double ny2_slope = (c.normal.y - b.normal.y) * invdy2;
        double nz2_slope = (c.normal.z - b.normal.z) * invdy2;

        for (int scanlineY = (int) cy; scanlineY > ay; scanlineY--)
        {
            final Vertex vertex1 = new Vertex
            (
                fvec((int)x1, scanlineY, z1, w1),
                fvec(u1, v1, tw1),
                fvec(nx1, ny1, nz1)
            );

            final Vertex vertex2 = new Vertex
            (
                fvec((int)x2, scanlineY, z2, w2),
                fvec(u2, v2, tw2),
                fvec(nx2, ny2, nz2)
            );

            line(image, vertex1, vertex2, texture);

            x1 -= x1_slope;
            z1 -= z1_slope;
            w1 -= w1_slope;
            
            u1 -= u1_slope;
            v1 -= v1_slope;
            tw1 -= tw1_slope;

            nx1 -= nx1_slope;
            ny1 -= ny1_slope;
            nz1 -= nz1_slope;

            x2 -= x2_slope;
            z2 -= z2_slope;
            w2 -= w2_slope;

            u2 -= u2_slope;
            v2 -= v2_slope;
            tw2 -= tw2_slope;

            nx2 -= nx2_slope;
            ny2 -= ny2_slope;
            nz2 -= nz2_slope;
        }
    }

    static Vector4 intersectPlane(Vector4 planePosition, Vector4 planeNormal, Vector4 lineStart, Vector4 lineEnd) 
    { 
        planeNormal = planeNormal.normalized(); 

        double planeD = -planeNormal.dot(planePosition); 

        double ad = lineStart.dot(planeNormal); 
        double bd = lineEnd  .dot(planeNormal); 

        double t = (-planeD - ad) / (bd - ad); 

        Vector4 lineStartToEnd  = lineEnd.minus(lineStart); 
        Vector4 lineToIntersect = lineStartToEnd.times(t); 

        return lineStart.plus(lineToIntersect); 
    }

    static double dist(Vector4 p, Vector4 planePosition, Vector4 planeNormal) 
    { 
        return planeNormal.dot(p.minus(planePosition)); 
    }
    
    static Triangle[] clipAgainstPlane(Vector4 planePosition, Vector4 planeNormal, Triangle triangle) 
    { 
		planeNormal = planeNormal.normalized();

		Vertex[] inside_points  = new Vertex[3]; int nInsidePointCount = 0;
		Vertex[] outside_points = new Vertex[3]; int nOutsidePointCount = 0;

		double d0 = dist(triangle.a, planePosition, planeNormal);
		double d1 = dist(triangle.b, planePosition, planeNormal);
		double d2 = dist(triangle.c, planePosition, planeNormal);

		if (d0 >= 0) inside_points [nInsidePointCount ++] = triangle.a;
		else         outside_points[nOutsidePointCount++] = triangle.a;

		if (d1 >= 0) inside_points [nInsidePointCount ++] = triangle.b;
		else         outside_points[nOutsidePointCount++] = triangle.b;

		if (d2 >= 0) inside_points [nInsidePointCount ++] = triangle.c;
		else         outside_points[nOutsidePointCount++] = triangle.c;

		if (nInsidePointCount == 0) return new Triangle[] { }; 
		if (nInsidePointCount == 3) return new Triangle[] { triangle };

		if (nInsidePointCount == 1 && nOutsidePointCount == 2)
		{
            double t = (planePosition.minus(inside_points[0])).dot(planeNormal) / (outside_points[0].minus(inside_points[0])).dot(planeNormal);

            Vector4 intersection1 = intersectPlane(planePosition, planeNormal, inside_points[0], outside_points[0]);
            Vector4 intersection2 = intersectPlane(planePosition, planeNormal, inside_points[0], outside_points[0]);
            
            Triangle newTriangle = new Triangle(
                inside_points[0],
                new Vertex
                (
                    intersection1,
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t),
                    Vector.lerp(inside_points[0].texture, outside_points[0].texture, t)
                ),
                new Vertex
                (
                    intersection2,
                    Vector.lerp(inside_points[0].texture, outside_points[1].texture, t),
                    Vector.lerp(inside_points[0].texture, outside_points[1].texture, t)
                )
            );

			return new Triangle[] { newTriangle };
		}

		if (nInsidePointCount == 2 && nOutsidePointCount == 1)
		{
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
                a.b,
                new Vertex(
                    intersectPlane(planePosition, planeNormal, inside_points[1], outside_points[0]),
                    Vector.lerp(inside_points[1].texture, outside_points[0].texture, t),
                    Vector.lerp(inside_points[1].texture, outside_points[0].texture, t)
                )
            );

			return new Triangle[] { a, b };
		}

        return new Triangle[] { };
    }

    static void mesh(final DrawableImage image, final Mesh mesh, final Matrix transform, final Matrix projection, final Matrix view, final Vector4 cameraPosition, final Image texture)
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

        for (int i = 0; i < clippedTriangles.length; i++)
        {
            triangle(image, clippedTriangles[i], texture);
        }
    }
}
