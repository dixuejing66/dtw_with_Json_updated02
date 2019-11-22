package dtw_algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Test {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		DTW dtw = new DTW();
		Test t = new Test();
		ArrayList<Trajectory> t1 = new ArrayList<Trajectory>();
		t1 = t.readJsonFile("E://TestData03/");

		for (int i = 0; i < t1.size(); i++) {
			for (int j = i + 1; j < t1.size(); j++) {
				System.out.println(dtw.calculateDTW(t1.get(i), t1.get(j)));
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("The running time of program is £º " + (end - start) + "ms");
	}

	public ArrayList<Trajectory> readJsonFile(String fileDir) {
		
		ArrayList<Trajectory> t = new ArrayList<Trajectory>();
	
		List<File> fileList = new ArrayList<File>();
		File file = new File(fileDir);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".json")) {
				fileList.add(f);
			} else if (f.isDirectory()) {
				readJsonFile(f.getAbsolutePath());
			}
		}

		for (int i = 0; i < fileList.size(); i++) {
			try {
				String content = FileUtils.readFileToString(fileList.get(i), "UTF-8");
				JSONObject jsonObject = new JSONObject(content);
				JSONArray getJsonArray = jsonObject.getJSONArray("locations");

				int num = getJsonArray.length();
				//System.out.println(num);
				double[] coordinate1_x = new double[num];
				double[] coordinate1_y = new double[num];
				Point[] coordinate1 = new Point[num];

				for (int j = 0; j < num; j++) {
					String[] array = getJsonArray.get(j).toString().split(":");
					String result_x = array[1].split(",")[0];
					String result_y = array[6].split(",")[0].replace("}", "");

					coordinate1_x[j] = Double.valueOf(result_x);
					coordinate1_y[j] = Double.valueOf(result_y);

					coordinate1[j] = new Point();
					coordinate1[j].y = coordinate1_x[j];
					coordinate1[j].x = coordinate1_y[j];
				}
				Trajectory tj = new Trajectory();
				tj.setPoints(coordinate1);
				t.add(tj);
				
			} catch (Exception e) {
				System.out.println("Exception");
				continue;
			}

		}
		return t;
	}

}
