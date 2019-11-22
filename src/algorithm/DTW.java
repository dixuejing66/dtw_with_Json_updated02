package algorithm;

public class DTW {
	Trajectory newTrajecotry;
	public double[][] distance;

	public double calculatedistance(Point p1, Point p2) {
		return Math.sqrt(
				(p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
	}

	public double calculateDTW(Trajectory t1, Trajectory t2) {
		// System.out.println("visit");
		double sum = 0.0;
		if (t1 == null) {
			System.out.println("t1 is null");
		}
		if (t2 == null) {
			System.out.println("t2 is null");
		}
		// System.out.println(t1.length()+"\t"+t2.length());
		if (t1.length() > t2.length()) {
			distance = new double[t1.length()][t2.length()];
			for (int i = 0; i < t1.length(); i++) {
				for (int j = 0; j < t2.length(); j++) {
					distance[i][j] = calculatedistance(t1.getPoints()[i], t2.getPoints()[j]);
				}
			}
		} else {
			distance = new double[t2.length()][t1.length()];
			for (int i = 0; i < t2.length(); i++) {
				for (int j = 0; j < t1.length(); j++) {
					distance[i][j] = calculatedistance(t2.getPoints()[i], t1.getPoints()[j]);
				}
			}
		}

		// System.out.println("dtw");
		sum = DTW1(t1, t2);
		return sum;
	}

	public double DTW1(Trajectory t1, Trajectory t2) {
		// System.out.println("dtw2");
		double sum = 0;
		if (t2.length() == 1) {
			for (int i = 1; i < t1.length(); i++) {
				sum += distance[i][0];
				return sum;
			}
		} else if (t1.length() == 1) {
			for (int j = 1; j < t2.length(); j++) {
				sum += distance[0][j];
				return sum;
			}
		}

		// System.out.println(distance[t1.length()-1][t2.length()-1]+Math.min(Math.min(DTW(getSubTrajectory(t1),
		// getSubTrajectory(t2)), DTW(t1, getSubTrajectory(t2))),DTW(t2,
		// getSubTrajectory(t1))));
		else if (t1.length() > t2.length()) {
			return distance[t1.length() - 1][t2.length() - 1]
					+ Math.min(Math.min(DTW1(newTrajectory(t1), newTrajectory(t2)), DTW1(t1, newTrajectory(t2))),
							DTW1(t2, newTrajectory(t1)));

		} else {
			return distance[t2.length() - 1][t1.length() - 1]
					+ Math.min(Math.min(DTW1(newTrajectory(t1), newTrajectory(t2)), DTW1(t1, newTrajectory(t2))),
							DTW1(t2, newTrajectory(t1)));

		}
		return sum;

	}

	public Trajectory newTrajectory(Trajectory t) {

		newTrajecotry = new Trajectory();
		Point[] p = new Point[t.getPoints().length - 1];
		for (int i = 0; i < t.getPoints().length - 1; i++)
			p[i] = t.getPoints()[i];
		newTrajecotry.setPoints(p);
		return newTrajecotry;
	}

}