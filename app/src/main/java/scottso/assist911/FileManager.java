package scottso.assist911;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by scottso on 2014-09-07.
 */
public class FileManager  {

    public static String[] fileNames;
    public static int accountCount;

    public static void saveFile(String workoutName, ArrayList<ListAccountItem> account, Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput(workoutName, Context.MODE_PRIVATE);
            for (int i = 0; i < account.size(); i++) {
                outputStream.write(account.get(i).getAccountName().getBytes());
                outputStream.write((""+"\n").getBytes());
                outputStream.write(account.get(i).getAccountAge());
                outputStream.write((""+"\n").getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test", "save called: " + workoutName);

        refreshFiles(context);
    }

    public static ArrayList<ListAccountItem> findAndReadFile(String workoutName, Context context) {
        refreshFiles(context);

        ArrayList<ListAccountItem> workout = new ArrayList<ListAccountItem>();
        boolean onExercise = true;

        try {
            FileInputStream fis = context.openFileInput(workoutName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            String name = "";
            int accountAge = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if (onExercise) {
                    name = line;
                    onExercise = false;
                } else {
                    accountAge = 0;
                    ListAccountItem item = new ListAccountItem(name, accountAge);
                    workout.add(item);
                    onExercise = true;
                }
            }
            fis.close();
            return workout;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFile(String workoutName, Context context) {
        context.deleteFile(workoutName);
        refreshFiles(context);
        Log.d("test", "delete called: " + workoutName);
    }

    //TODO: make routines editable
    public static void editExerciseInFile(String workoutName, String toReplaceExercise, String replacementExercise,
                                          String toReplaceTime, String replacementTime, Context context) {
        try {
            FileInputStream fis = context.openFileInput(workoutName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line == toReplaceExercise) {
                    //dostuff etc
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshFiles(context);
    }

    public static void editAddToFile(String workoutName, String exercise, Context context) {
        try {
            FileInputStream fis = context.openFileInput(workoutName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line == exercise) {
                    //TODO: do this part too
                }
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshFiles(context);
    }

    public void editDeleteFromFile(String workoutName, String exercise, Context context) {
        //TODO: do this
    }

    public static void refreshFiles(Context context) {
        fileNames = context.fileList();
        accountCount = fileNames.length;
    }
}

