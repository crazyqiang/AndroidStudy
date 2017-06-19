package org.ninetripods.mq.study.util;

import android.graphics.PointF;

/**
 * Created by MQ on 2017/6/16.
 */

public class MathUtil {


    /**
     * 获得两点之间的直线距离
     *
     * @param p1 PointF
     * @param p2 PointF
     * @return 两点之间的直线距离
     */
    public static float getTwoPointDistance(PointF p1, PointF p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    /**
     * 根据两个点(x1,y1)(x2,y2)的坐标算出斜率
     *
     * @param x1 x1
     * @param x2 x2
     * @param y1 y1
     * @param y2 y2
     * @return 斜率
     */
    public static Float getLineSlope(float x1, float x2, float y1, float y2) {
        if (x2 - x1 == 0) return null;
        return (y2 - y1) / (x2 - x1);
    }

    /**
     * 根据传入的两点得到斜率
     *
     * @param p1 PointF
     * @param p2 PointF
     * @return 返回斜率
     */
    public static Float getLineSlope(PointF p1, PointF p2) {
        if (p2.x - p1.x == 0) return null;
        return (p2.y - p1.y) / (p2.x - p1.x);
    }

    /**
     * Get middle point between p1 and p2.
     * 获得两点连线的中点
     *
     * @param p1 PointF
     * @param p2 PointF
     * @return 中点
     */
    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }


    /**
     * Get the point of intersection between circle and line.
     * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
     *
     * @param pMiddle The circle center point.
     * @param radius  The circle radius.
     * @param lineK   The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Float lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        return points;
    }


}
