package pretty.vivi.tcp.sever.handler;

import org.apache.log4j.Logger;

import pretty.vivi.tcp.Server.TriangleType;

public class Triangle implements GeometryHandler {

	Logger log = (Logger) Logger.getLogger(getClass());

	@Override
	public String check(int[] points) {
		TriangleType check = checkTriangle(points[0], points[1], points[2], points[3], points[4], points[5]);

		switch (check) {
			case TAM_GIAC:
				return "It is normal triangle";

			case TAM_GIAC_VUONG:
				return "Tam giac vuong";

			case TAM_GIAC_CAN:
				return "It is isosceles triangle";

			case TAM_GIAC_DEU:
				return "It is equilateral triangle";

			default:
				return "It is not Triangle!";
		}
	}

	protected TriangleType checkTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		double sideAB = side(x1, y1, x2, y2);
		double sideBC = side(x2, y2, x3, y3);
		double sideCA = side(x3, y3, x1, y1);

		if (sideAB == sideBC && sideBC == sideCA)
			return TriangleType.TAM_GIAC_DEU;
		if (sideAB == sideBC || sideBC == sideCA || sideCA == sideAB) {
			if (sideAB * sideAB + sideBC * sideBC == sideCA * sideCA || sideAB * sideAB + sideCA * sideCA == sideBC * sideBC
					|| sideBC * sideBC + sideCA * sideCA == sideAB * sideAB)
				return TriangleType.TAM_GIAC_VUONG;
			return TriangleType.TAM_GIAC_CAN;
		}
		if (sideAB * sideAB + sideBC * sideBC == sideCA * sideCA || sideAB * sideAB + sideCA * sideCA == sideBC * sideBC
				|| sideBC * sideBC + sideCA * sideCA == sideAB * sideAB)
			return TriangleType.TAM_GIAC_VUONG;
		return TriangleType.TAM_GIAC;
	}

	protected boolean isPointInsideTriangle(int px, int py, int x1, int y1, int x2, int y2, int x3, int y3) {
		double AB = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		double BC = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
		double CA = Math.sqrt((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1));

		double PA = Math.sqrt((px - x1) * (px - x1) + (py - y1) * (py - y1));
		double PB = Math.sqrt((px - x2) * (px - x2) + (py - y2) * (py - y2));
		double PC = Math.sqrt((px - x3) * (px - x3) + (py - y3) * (py - y3));

		double sABC = triangleArea(AB, BC, CA);
		double sPAB = triangleArea(PA, PB, AB);
		double sPBC = triangleArea(PB, PC, BC);
		double sPCA = triangleArea(PC, PA, CA);

		if (sPAB + sPBC + sPCA == sABC)
			return true;
		return false;
	}

	protected double side(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	protected double triangleArea(double a, double b, double c) {
		return Math.sqrt((a + b + c) / 2 * ((a + b + c) / 2 - a) * ((a + b + c) / 2 - b) * ((a + b + c) / 2 - c));
	}
}
