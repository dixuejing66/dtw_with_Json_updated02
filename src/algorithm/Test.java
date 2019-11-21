package algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Test {

	// public List<Trajectory> locations;

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		DTW dtw = new DTW();
		Test t = new Test();
		ArrayList<Trajectory> t1 = new ArrayList<Trajectory>();
		t1 = t.readJsonFile("E://TestData02/");
		// Trajectory[] t1 = t.readJsonFile();
		for (int i = 0; i < t1.size(); i++) {
			// System.out.println(t1.size());
			for (int j = i + 1; j < t1.size(); j++) {
				// System.out.println(t1.get(i));
				// dtw.calculateDTW(t1[i], t1[j]);
				// System.out.println(t1[0].length());
				System.out.println(dtw.calculateDTW(t1.get(i), t1.get(j)));
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("The running time of program is £º " + (end - start) + "ms");

	}

	public ArrayList<Trajectory> readJsonFile(String fileDir) {

		ArrayList<Trajectory> t = new ArrayList<Trajectory>();
		Trajectory tj = new Trajectory();
		List<File> fileList = new ArrayList<File>();
		File file = new File(fileDir);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".json")) {
				fileList.add(f);
				// System.out.println(fileList.size());
			} else if (f.isDirectory()) {
				// System.out.println(f.getAbsolutePath());
				readJsonFile(f.getAbsolutePath());
			}
		}

		for (int i = 0; i < fileList.size(); i++) {
			// System.out.println(fileList.size());
			// System.out.println(fileList.get(i).getName());
			// System.out.println(f1.getName());
			try {
				// File file = new File("E://data/" + "/1 (" + numof + ").json");
				String content = FileUtils.readFileToString(fileList.get(i), "UTF-8");
				JSONObject jsonObject = new JSONObject(content);
				JSONArray getJsonArray = jsonObject.getJSONArray("locations");

				int num = getJsonArray.length();
				System.out.println(num);
				double[] coordinate1_x = new double[num];
				double[] coordinate1_y = new double[num];
				Point[] coordinate1 = new Point[num];

				for (int j = 0; j < num; j++) {
					// System.out.println(j);
					// System.out.println(getJsonArray.get(j).toString());
					String[] array = getJsonArray.get(j).toString().split(":");
					String result_x = array[1].split(",")[0];
					// System.out.println(result_x);
					String result_y = array[6].split(",")[0].replace("}", "");
					// System.out.println(result_y);
					// String time = array[0].split(",")[0];
					coordinate1_x[j] = Double.valueOf(result_x);
					coordinate1_y[j] = Double.valueOf(result_y);

					coordinate1[j] = new Point();
					coordinate1[j].y = coordinate1_x[j];
					coordinate1[j].x = coordinate1_y[j];
				}

				tj.setPoints(coordinate1);
				t.add(tj);
				// System.out.println(t.size());
				// t1[numof-1].getPoints();
				// System.out.println(t.get(num));
			} catch (Exception e) {
				System.out.println("Exception");
				continue;
			}

		}
		return t;
	}

}
