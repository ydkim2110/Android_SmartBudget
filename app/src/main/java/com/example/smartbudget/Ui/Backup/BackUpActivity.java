package com.example.smartbudget.Ui.Backup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BackUpActivity extends AppCompatActivity {

    private static final String TAG = BackUpActivity.class.getSimpleName();
    public static final String ROOT_DOWNLOAD_DIR =
            Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "BackupApp" + File.separator;
    @BindView(R.id.btn_back_up)
    Button btn_back_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        btn_back_up.setOnClickListener(v -> {
            Dexter.withActivity(BackUpActivity.this)
                    .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                new ExportDatabaseCSVTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            } else {
                                new ExportDatabaseCSVTask().execute();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        }
                    })
                    .check();
        });

    }

    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog mDialog = new ProgressDialog(BackUpActivity.this);
        private BudgetDatabase mBudgetDatabase;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mDialog.setMessage("Exporting database...");
            this.mDialog.show();
            mBudgetDatabase = BudgetDatabase.getInstance(BackUpActivity.this);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            File exportDir = new File(ROOT_DOWNLOAD_DIR);

            Log.d(TAG, "doInBackground: exportDir: "+exportDir);

            if (!exportDir.exists()) {
                Log.d(TAG, "exportDir.exists(): called!!");
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "mySmartBudget.csv");

            try {
                Log.d(TAG, "doInBackground: wirter success!!");
                file.createNewFile();
                //CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
                CSVWriter csvWriter = new CSVWriter(file);
                String[] column = {"id", "name", "description", "amount", "high_category", "type", "create_at", "currency"};
                csvWriter.writeNext(column);
                List<AccountItem> account = mBudgetDatabase.accountDAO().getAllAccounts();
                for (int i = 0; i < account.size(); i++) {
                    String[] mySecondStringArray = {String.valueOf(account.get(i).getId()),
                            account.get(i).getName(),
                            account.get(i).getDescription(),
                            String.valueOf(account.get(i).getAmount()),
                            account.get(i).getHighCategory(),
                            account.get(i).getType(),
                            account.get(i).getCreateAt(),
                            account.get(i).getCurrency()
                    };
                    csvWriter.writeNext(mySecondStringArray);
                }
                csvWriter.close();
                return true;
            } catch (IOException e) {
                Log.d(TAG, "doInBackground: exception: "+e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (this.mDialog.isShowing())
                this.mDialog.dismiss();
            if (success) {
                Toast.makeText(BackUpActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BackUpActivity.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class CSVWriter {
        private PrintWriter pw;
        private char separator;
        private char escapechar;
        private String lineEnd;
        private char quotechar;
        public static final char DEFAULT_SEPARATOR = ',';
        public static final char NO_QUOTE_CHARACTER = '\u0000';
        public static final char NO_ESCAPE_CHARACTER = '\u0000';
        public static final String DEFAULT_LINE_END = "\n";
        public static final char DEFAULT_QUOTE_CHARACTER = '"';
        public static final char DEFAULT_ESCAPE_CHARACTER = '"';

        public CSVWriter(File file) {
            this(file, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
                    DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
        }

        public CSVWriter(File file, char separator, char quotechar, char escapechar, String lineEnd) {

            Writer writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                Log.d(TAG, "CSVWriter: Success!!");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            this.pw = new PrintWriter(writer);
            this.separator = separator;
            this.quotechar = quotechar;
            this.escapechar = escapechar;
            this.lineEnd = lineEnd;
        }

        public void writeNext(String[] nextLine) {
            if (nextLine == null)
                return;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nextLine.length; i++) {

                if (i != 0) {
                    sb.append(separator);
                }
                String nextElement = nextLine[i];
                if (nextElement == null)
                    continue;
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
                for (int j = 0; j < nextElement.length(); j++) {
                    char nextChar = nextElement.charAt(j);
                    if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                        sb.append(escapechar).append(nextChar);
                    } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                        sb.append(escapechar).append(nextChar);
                    } else {
                        sb.append(nextChar);
                    }
                }
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
            }
            sb.append(lineEnd);
            pw.write(sb.toString());
        }

        public void close() throws IOException {
            pw.flush();
            pw.close();
        }

        public void flush() throws IOException {
            pw.flush();
        }
    }
}
