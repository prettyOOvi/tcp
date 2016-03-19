package pretty.vivi.tcp.sever.handler;

public interface GeometryHandler {
	
	public String check(int[] points);

	public class Factory {

		public static GeometryHandler getHandler(int[] points) {
			if (points == null) {
				return null;
			}

			int numPoints = points.length;
			if (numPoints == 6) {
				return new Triangle();
			}
			return null;
		}

	}

}
