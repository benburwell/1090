package com.benburwell.planes.gui;

import java.awt.Color;

/**
 * @author ben
 */
public class GraphicsTheme {
    public static class Colors {
        public static final Color BASE_0 = new Color(12, 16, 20);
        public static final Color BASE_1 = new Color(17, 21, 28);
        public static final Color BASE_2 = new Color(9, 31, 46);
        public static final Color BASE_3 = new Color(10, 55, 73);
        public static final Color BASE_4 = new Color(36, 83, 97);
        public static final Color BASE_5 = new Color(89, 156, 171);
        public static final Color BASE_6 = new Color(153, 209, 206);
        public static final Color BASE_7 = new Color(211, 235, 233);
        public static final Color RED = new Color(194, 49, 39);
        public static final Color ORANGE = new Color(210, 105, 55);
        public static final Color YELLOW = new Color(237, 180, 67);
        public static final Color MAGENTA = new Color(136, 140, 166);
        public static final Color VIOLET = new Color(78, 81, 102);
        public static final Color BLUE = new Color(25, 84, 102);
        public static final Color CYAN = new Color(51, 133, 158);
        public static final Color GREEN = new Color(42, 168, 137);
    }

    public static class Styles {
        public static final Color MAP_BACKGROUND_COLOR = Colors.BASE_0;
        public static final Color MAP_RANGE_COLOR = Colors.BASE_3;
        public static final Color MAP_NAVAID_COLOR = Colors.VIOLET;
        public static final Color MAP_AIRPORT_COLOR = Colors.MAGENTA;
        public static final Color MAP_ROUTE_COLOR = Colors.BASE_2;
        public static final Color MAP_LABEL_COLOR = Colors.CYAN;
        public static final Color MAP_PLANE_MIN_COLOR = Colors.RED;
        public static final Color MAP_PLANE_MAX_COLOR = Colors.GREEN;
        public static final Color MAP_PLANE_TRACK_COLOR = Colors.BASE_4;
        public static final Color MAP_RUNWAY_BORDER_COLOR = Colors.BLUE;
    }
}
