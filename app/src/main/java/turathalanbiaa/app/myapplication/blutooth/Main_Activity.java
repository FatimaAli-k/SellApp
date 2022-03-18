package turathalanbiaa.app.myapplication.blutooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import turathalanbiaa.app.myapplication.Model.Item;
import turathalanbiaa.app.myapplication.Model.SellMenuItem;
import turathalanbiaa.app.myapplication.R;
import turathalanbiaa.app.myapplication.RecyclerItemTouchHelper;
import turathalanbiaa.app.myapplication.ScanActivity;
import turathalanbiaa.app.myapplication.Search_activity;
import turathalanbiaa.app.myapplication.ServerInfo;
import turathalanbiaa.app.myapplication.SettingsActivity;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;


import turathalanbiaa.app.myapplication.WebActivity;
import turathalanbiaa.app.myapplication.ZxingScan;
import turathalanbiaa.app.myapplication.command.Command;
import turathalanbiaa.app.myapplication.command.PrintPicture;
import turathalanbiaa.app.myapplication.command.PrinterCommand;


import turathalanbiaa.app.myapplication.mlkittext.ScanMainActivity;
import turathalanbiaa.app.myapplication.volley.AppController;
import turathalanbiaa.app.myapplication.volley.VolleySingleton;
import zj.com.customize.sdk.Other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static java.lang.Thread.sleep;


public class Main_Activity extends Activity implements OnClickListener, MyRecyclerViewAdapter.ItemClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    /******************************************************************************************************/
    // Debugging
    private static final String TAG = "Main_Activity";
    private static final boolean DEBUG = true;
    /******************************************************************************************************/
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;
    /*******************************************************************************************************/
    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    private String path;
    WebView web_view;
    WebView web_view2;
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CHOSE_BMP = 3;
    private static final int REQUEST_CAMER = 4;

    //QRcode
    private static final int QR_WIDTH = 350;
    private static final int QR_HEIGHT = 350;
    /*******************************************************************************************************/
    private static final String CHINESE = "GBK";
    private static final String THAI = "CP874";
    private static final String KOREAN = "EUC-KR";
    private static final String BIG5 = "BIG5";
    private static final String ARBIC = "Arabic";

    /*********************************************************************************/
//	private TextView mTitle;
    EditText editText;
    TextView username;
    ImageView imageViewPicture;
    private static boolean is58mm = true;
    private RadioButton width_58mm, width_80;
    private RadioButton thai, big5, Simplified, Korean;
    private CheckBox hexBox;
    private Button sendButton = null;
    private Button saveButton = null;
    private Button testButton = null;
    private Button printbmpButton = null;
    private Button btnScanButton = null;
    private Button btn_sitting = null;
    private Button btnClose = null;
    private Button btn_BMP = null;
    private Button btn_ChoseCommand = null;
    private Button btn_prtsma = null;
    private Button btn_prttableButton = null;
    private Button btn_prtcodeButton = null;
    private Button btn_scqrcode = null;
    private Button btn_camer = null;

    /******************************************************************************************************/
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the services
    private BluetoothService mService = null;

    /***************************   指                 令****************************************************************/
    final String[] items = {"复位打印机", "打印并走纸", "标准ASCII字体", "压缩ASCII字体", "正常大小",
            "二倍高倍宽", "三倍高倍宽", "四倍高倍宽", "取消加粗模式", "选择加粗模式", "取消倒置打印", "选择倒置打印", "取消黑白反显", "选择黑白反显",
            "取消顺时针旋转90°", "选择顺时针旋转90°", "走纸到切刀位置并切纸", "蜂鸣指令", "标准钱箱指令",
            "实时弹钱箱指令", "进入字符模式", "进入中文模式", "打印自检页", "禁止按键", "取消禁止按键",
            "设置汉字字符下划线", "取消汉字字符下划线", "进入十六进制模式"};
    final String[] itemsen = {"Print Init", "Print and Paper", "Standard ASCII font", "Compressed ASCII font", "Normal size",
            "Double high power wide", "Twice as high power wide", "Three times the high-powered wide", "Off emphasized mode", "Choose bold mode", "Cancel inverted Print", "Invert selection Print", "Cancel black and white reverse display", "Choose black and white reverse display",
            "Cancel rotated clockwise 90 °", "Select the clockwise rotation of 90 °", "Feed paper Cut", "Beep", "Standard CashBox",
            "Open CashBox", "Char Mode", "Chinese Mode", "Print SelfTest", "DisEnable Button", "Enable Button",
            "Set Underline", "Cancel Underline", "Hex Mode"};
    final byte[][] byteCommands = {
            {0x1b, 0x40, 0x0a},// 复位打印机
            {0x0a}, //打印并走纸
            {0x1b, 0x4d, 0x00},// 标准ASCII字体
            {0x1b, 0x4d, 0x01},// 压缩ASCII字体
            {0x1d, 0x21, 0x00},// 字体不放大
            {0x1d, 0x21, 0x11},// 宽高加倍
            {0x1d, 0x21, 0x22},// 宽高加倍
            {0x1d, 0x21, 0x33},// 宽高加倍
            {0x1b, 0x45, 0x00},// 取消加粗模式
            {0x1b, 0x45, 0x01},// 选择加粗模式
            {0x1b, 0x7b, 0x00},// 取消倒置打印
            {0x1b, 0x7b, 0x01},// 选择倒置打印
            {0x1d, 0x42, 0x00},// 取消黑白反显
            {0x1d, 0x42, 0x01},// 选择黑白反显
            {0x1b, 0x56, 0x00},// 取消顺时针旋转90°
            {0x1b, 0x56, 0x01},// 选择顺时针旋转90°
            {0x0a, 0x1d, 0x56, 0x42, 0x01, 0x0a},//切刀指令
            {0x1b, 0x42, 0x03, 0x03},//蜂鸣指令
            {0x1b, 0x70, 0x00, 0x50, 0x50},//钱箱指令
            {0x10, 0x14, 0x00, 0x05, 0x05},//实时弹钱箱指令
            {0x1c, 0x2e},// 进入字符模式
            {0x1c, 0x26}, //进入中文模式
            {0x1f, 0x11, 0x04}, //打印自检页
            {0x1b, 0x63, 0x35, 0x01}, //禁止按键
            {0x1b, 0x63, 0x35, 0x00}, //取消禁止按键
            {0x1b, 0x2d, 0x02, 0x1c, 0x2d, 0x02}, //设置下划线
            {0x1b, 0x2d, 0x00, 0x1c, 0x2d, 0x00}, //取消下划线
            {0x1f, 0x11, 0x03}, //打印机进入16进制模式
    };
    /***************************条                          码***************************************************************/
    final String[] codebar = {"UPC_A", "UPC_E", "JAN13(EAN13)", "JAN8(EAN8)",
            "CODE39", "ITF", "CODABAR", "CODE93", "CODE128", "QR Code"};
    final byte[][] byteCodebar = {
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
            {0x1b, 0x40},// 复位打印机
    };

    /******************************************************************************************************/

    //

    LinearLayout Layout;
    MyRecyclerViewAdapter adapter;
    ArrayList<SellMenuItem> menuItems = new ArrayList<>();
    SellMenuItem SellItem = new SellMenuItem();
    Item iteminfo = new Item();
    ArrayList<Item> itemArrayList = new ArrayList<>();
    ProgressDialog pDialog;
    SessionManager session;
    String code = "";
    String sellMenuId = "";
    //buttons
    Button additem, logout, newCustomer, oldCustomer, clearData, cardCustomer ,btn_card_number;

    TextView menuIdTextView;
    TextView user_name;

    //urls
    String addSellMenuItemURL;
    String updatURL;
    String createNewMenuURL;
    String getOldMenuURL;
    String deleteItemURL;
    String getItemURL;
    String getCardURL;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_URLs();

        if (DEBUG)
            Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main_activity);

        //session
        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        if (session.isLoggedIn()) {
//            String name = session.getshared("name");
//            Toast.makeText(getApplicationContext(),
//                    "اهلا بك : " + name,
//                    Toast.LENGTH_LONG).show();
            session.createBarcode("");
        }

//
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("انتظر قليلا...");
        pDialog.setCancelable(false);


        _declaration();
        _click_listener();
       _webviewSetting();


       _load_customer_webview();


        menuIdTextView = findViewById(R.id.textView_sellMenuId);
        menuIdTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer menu_id = Integer.valueOf(menuIdTextView.getText().toString());
                show_menu(menu_id);


            }
        });

        //clear btn
        clearData = findViewById(R.id.button_clear);
        clearData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                clearItemData();

            }
        });
        username = (TextView) findViewById(R.id.user_name);

        username.setText(session.getshared("name"));
        //add SellItem
        additem = findViewById(R.id.button_add_item);
        additem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                set_view_for(1);
                TextView txtsellMenuId = findViewById(R.id.textView_sellMenuId);
                if (txtsellMenuId.getText().equals("0") || txtsellMenuId.getText().equals("")) {
                    Toast.makeText(getBaseContext(), "لا توجد قائمة", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent;
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String setting_barcode = sharedPreferences.getString("setting_barcode", "2");

                session.setScanfor("2");  //for item

                if (setting_barcode.equals("1")) {

                    intent = new Intent(getBaseContext(), ScanActivity.class);

                } else if (setting_barcode.equals("2")) {
                    intent = new Intent(getBaseContext(), ScanMainActivity.class);

                } else {
                    intent = new Intent(getBaseContext(), ZxingScan.class);

                }
                startActivity(intent);

            }
        });

       Button btn_search = (Button) findViewById(R.id.btn_search_Customer);
        btn_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                open_search();

            }
        });


        //logout
        logout = findViewById(R.id.button_logout);
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();

            }
        });

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String print_btn = sharedPreferences.getString("print_btn", "2");

        sendButton = findViewById(R.id.Send_Button);
        if(print_btn.equals("2"))
        {
            sendButton.setVisibility(View.GONE);


        }else
        {
            sendButton.setVisibility(View.VISIBLE);

        }


        String new_customer = sharedPreferences.getString("new_customer", "2");

        //new customer
        newCustomer = findViewById(R.id.button_newCustomer);
        if(new_customer.equals("2"))
        {
            newCustomer.setVisibility(View.GONE);
        }
        newCustomer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //send get request retreive sell menu id
//                sellMenuId="12345678";
                clearItemData();
                set_view_for(1);
                getSellMenuId();


            }
        });


        cardCustomer = findViewById(R.id.button_card);
        cardCustomer. setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                show_input();
                return true;
            }
        });


        Button customer_Button = (Button) findViewById(R.id.customer_Button);
        Button hide_web2_button = (Button) findViewById(R.id.hide_web2_button);

        customer_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show_web2(true);


            }
        });
        hide_web2_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show_web2(false);


            }
        });

        cardCustomer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //scan for sell menu
                clearItemData();
                set_view_for(1);
                Intent intent;
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String setting_barcode = sharedPreferences.getString("setting_barcode", "1");
                session.setScanfor("3");  //for item

                if (setting_barcode.equals("1")) {
                    intent = new Intent(getBaseContext(), ScanActivity.class);
                } else if (setting_barcode.equals("2")) {
                    intent = new Intent(getBaseContext(), ScanMainActivity.class);
                } else {
                    intent = new Intent(getBaseContext(), ZxingScan.class);
                }

                startActivity(intent);


            }
        });

//        btn_card_number = findViewById(R.id.btn_card_number);
//        btn_card_number.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
        //old customer
        oldCustomer = findViewById(R.id.button_oldCustomer);
        oldCustomer. setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                show_input2();
                return true;
            }
        });
        oldCustomer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                //scan for sell menu
                clearItemData();
                set_view_for(1);
                Intent intent;
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String setting_barcode = sharedPreferences.getString("setting_barcode", "1");
                session.setScanfor("1");  //for item

                if (setting_barcode.equals("1")) {
                    intent = new Intent(getBaseContext(), ScanActivity.class);
                } else if (setting_barcode.equals("2")) {
                    intent = new Intent(getBaseContext(), ScanMainActivity.class);
                } else {
                    intent = new Intent(getBaseContext(), ZxingScan.class);
                }

                startActivity(intent);


            }
        });


//
        RecyclerView recyclerView = findViewById(R.id.items_recycler_view);
        Layout = findViewById(R.id.liner_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), menuItems, itemArrayList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        //// Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        //check internet connection
        boolean connectedToWifi = haveNetworkConnection();
        if (!connectedToWifi) {
            Toast.makeText(this, "WiFi is not available",
                    Toast.LENGTH_LONG).show();

        }

    }

    private void open_search() {
        Intent i = new Intent(this , Search_activity.class);
        startActivity(i);
    }

    private void show_web2(Boolean value) {
        LinearLayout li_web2 = (LinearLayout) findViewById(R.id.li_web2);
        if(value )
        {

            li_web2.setVisibility(View.VISIBLE);

        }else
        {
            li_web2.setVisibility(View.GONE);

        }

    }

    private void _load_customer_webview() {



        web_view2 = findViewById(R.id.web2);

        web_view2.getSettings().setLightTouchEnabled(true);
        web_view2.getSettings().setJavaScriptEnabled(true);
        web_view2.getSettings().setAppCacheEnabled(true);
        Intent i = getIntent();
        Integer type = i.getIntExtra("type",1);
        String user_name ;
        String user_id;
        Integer menu_id;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        path = sharedPreferences.getString("server_path", "192.168.0.125");
        SessionManager session;
        session = new SessionManager(getApplicationContext());
        String url;
        url  ="http://" + path + "/customers/" ;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("انتظر قليلا");
        progressDialog.setCancelable(true);
        progressDialog.show();
        web_view2.loadUrl(url);
        web_view2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();

                super.onPageFinished(view, url);

            }

        });

    }

    private void show_input2() {
        clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ادخل رقم القائمة");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("تم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sellMenuId = input.getText().toString();;
                menuIdTextView = findViewById(R.id.textView_sellMenuId);
                menuIdTextView.setText(sellMenuId);
                getSellMenuItemsArray();
                session.setScanfor("0");


            }
        });
//        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });

        builder.show();
    }
    public void clear() {
        int size = menuItems.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                menuItems.remove(0);
            }


        }
    }
    private void show_input() {
//        RecyclerView recyclerView = findViewById(R.id.items_recycler_view);
//        recyclerView.setAdapter(null);
//        recyclerView.setAdapter(adapter);
        clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ادخل رقم البطاقة");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("تم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String Customer_id = input.getText().toString();
                getMenuID_Card(getCardURL, Customer_id);
                Log.d("karrar", "getMenuID_Card func " + Customer_id);
                session.setScanfor("0");

            }
        });
//        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });

        builder.show();
    }

    private void _webviewSetting() {

        web_view = findViewById(R.id.web1);
        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setGeolocationEnabled(true);
        web_view.setSoundEffectsEnabled(true);
        web_view.getSettings().setAppCacheEnabled(true);


        web_view2 = findViewById(R.id.web2);
        web_view2.requestFocus();
        web_view2.getSettings().setLightTouchEnabled(true);
        web_view2.getSettings().setJavaScriptEnabled(true);
        web_view2.getSettings().setGeolocationEnabled(true);
        web_view2.setSoundEffectsEnabled(true);
        web_view2.getSettings().setAppCacheEnabled(true);


    }

    private void show_menu(Integer menu_id) {



        Intent i = getIntent();
        String user_name;
        String user_id;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        path = sharedPreferences.getString("server_path", "192.168.0.125");
        SessionManager session;
        session = new SessionManager(getApplicationContext());
        user_name = session.getshared("name");
        user_id = session.getshared("id");


 ////for replace
        Intent ac = new Intent(getBaseContext(), WebActivity.class);
        ac.putExtra("type", 2);
        ac.putExtra("menu_id", menu_id);
        startActivity(ac);

//
//
//        String url;
//        url = "http://" + path + "/user-edit-menu/" + menu_id + "/" + user_id + "/" + user_name;
//
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("انتظر قليلا...");
//        progressDialog.setCancelable(false);
//
//        web_view.loadUrl(url);
//        web_view.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                progressDialog.show();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                progressDialog.hide();
//            }
//
//        });
//        set_view_for(2);

    }

    private void set_view_for(int i) {

        LinearLayout li_1 = (LinearLayout) findViewById(R.id.li_1);
        LinearLayout li_2 = (LinearLayout) findViewById(R.id.li_2);
        LinearLayout li_3 = (LinearLayout) findViewById(R.id.li_3);
        if (i == 1) {
            li_1.setVisibility(View.VISIBLE);
            li_3.setVisibility(View.VISIBLE);
            li_2.setVisibility(View.GONE);

        } else {
            li_1.setVisibility(View.GONE);
            li_3.setVisibility(View.GONE);
            li_2.setVisibility(View.VISIBLE);

        }

    }


    private void get_URLs() {
        turathalanbiaa.app.myapplication.ServerInfo ServerInfo = new ServerInfo(this);
        addSellMenuItemURL = ServerInfo.getUrl("sellmenuitem");
        updatURL = ServerInfo.getUrl("update");
        createNewMenuURL = ServerInfo.getUrl("newsellmenu");
        getOldMenuURL = ServerInfo.getUrl("oldmenu");

        deleteItemURL = ServerInfo.getUrl("delete");
        getItemURL = ServerInfo.getUrl("item");
        getCardURL = ServerInfo.getUrl("card");

    }

    private void _declaration() {
        btn_sitting = (Button) findViewById(R.id.btn_sitting);
        user_name = (TextView) findViewById(R.id.user_name);

    }

    private void _click_listener() {
        btn_sitting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(i);
            }
        });

        user_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), WebActivity.class);
                i.putExtra("type", 1);
                startActivity(i);


            }
        });
    }


    @Override
    public void onBackPressed() {


    }

    @Override
    public void onStart() {
        super.onStart();

//         If Bluetooth is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the session
        } else {
            if (mService == null)
                KeyListenerInit();//监听
        }
    }

    String msgPrintFormat() {

        String msg = session.getshared("name");
        //final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        String currentDate = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd", Locale.ENGLISH).format(new Date());
        msg += "\n" + currentDate;
        String color;
        int len = menuItems.size();
        for (int i = 0; i < len; i++) {
//
            color = "";

            String s;
            s = menuItems.get(i).getItem_price().toString();
            if (menuItems.get(i).getF4() != null && menuItems.get(i).getF4() != "null") {
                color = " | " + menuItems.get(i).getF4();
            }
            msg += "\n" + "_____________________________";

            msg += "\n" + "   " + s + menuItems.get(i).getItem_name() + color;


        }
        return msg;
    }

    void sendToDB() {
        //add to sell SellItem
//
        for (int i = 0; i < menuItems.size(); i++) {
            Map<String, String> params = new HashMap<>();
            params.put("user_sell_it_id", session.getshared("id"));
            params.put("sell_menu_id", sellMenuId);
            params.put("item_name", menuItems.get(i).getItem_name());
            params.put("item_price", menuItems.get(i).getItem_price().toString());
            params.put("item_count", menuItems.get(i).getItem_count().toString());
            params.put("item_id", menuItems.get(i).getItem_id().toString());
            params.put("f4", menuItems.get(i).getF4());
            //  params.put("item_cost",  String.valueOf(menuItems.get(i).getItem_cost());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            String dt = sdf.format(new Date());
//            params.put("datetime", dt);

//
            if (menuItems.get(i).getId() == null) {

                sendItems(addSellMenuItemURL, params);

            } else {
                //updateitems using the id and count
                Map<String, String> upParams = new HashMap<>();
                upParams.put("id", menuItems.get(i).getId().toString());
                upParams.put("item_count", menuItems.get(i).getItem_count().toString());
                //  upParams.put("datetime", dt);
                sendItems(updatURL, upParams);
            }



        }


            show_menu(Integer.valueOf(sellMenuId));


    }

    void clearItemData() {
        TextView txtview = findViewById(R.id.textView_sellMenuId);
        txtview.setText("");
        menuItems.clear();
        itemArrayList.clear();
//        iteminfo=new Item();
//        SellItem=new SellMenuItem();
        sellMenuId = "";
        session.createBarcode("");
        adapter.notifyDataSetChanged();

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;

        }
        return haveConnectedWifi;
    }

    void sendItems(String url, Map<String, String> params) {


        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject jsonItem = (JSONObject) response;
//

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "لاتوجد بيانات لهذا الباركود   " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "لاتوجد بيانات لهذا الباركود   " + error.getMessage());
                hidepDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }


    void getSellMenuId() {

        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                createNewMenuURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    sellMenuId = response.getString("id");

                    menuIdTextView.setText(sellMenuId);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "لاتوجد استجابة   "+ e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "لاتوجد استجابة   " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }
    void getCustomer() {

        showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                createNewMenuURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    sellMenuId = response.getString("id");

                    menuIdTextView.setText(sellMenuId);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "لاتوجد استجابة   "+ e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "لاتوجد استجابة   " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }

    private void getSellMenuItemsArray() {

//
        showpDialog();
        Log.d("karrar", "start ");
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, getOldMenuURL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("karrar", "responsed ");
                        try {
                            //converting response to json object

                            JSONArray obj = new JSONArray(response);


                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonItem = (JSONObject) obj
                                        .get(i);

                                String t = jsonItem.getString("sell_menu_item");

                                JSONArray obj2 = new JSONArray(t);

                                for (int j = 0; j < obj2.length(); j++) {
                                    JSONObject jsonItem2 = (JSONObject) obj2
                                            .get(j);

//
                                    Log.d("karrar", jsonItem2.getString("item_name"));
                                    int id = jsonItem2.getInt("id");
                                    String name = jsonItem2.getString("item_name");
                                    int price = jsonItem2.getInt("item_price");
                                    int count = jsonItem2.getInt("item_count");
                                    int itemid = jsonItem2.getInt("item_id");

                                    String f4 = jsonItem2.getString("f4");


                                    iteminfo = new Item();
                                    itemArrayList.add(iteminfo);


                                    SellItem = new SellMenuItem();
                                    SellItem.setItem_count(count);
                                    SellItem.setId(id);
                                    SellItem.setItem_name(name);
                                    SellItem.setItem_price(price);
                                    SellItem.setItem_id(itemid);
                                    //details

                                    SellItem.setF4(f4);
                                    menuItems.add(SellItem);
                                }

                            }

//
//
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    " // " + e, Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        hidepDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", sellMenuId);

                params.put("user_sell_it_id", session.getshared("id"));

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void getItemObj(String url) {
        Map<String, String> params = new HashMap<>();
        params.put("barcode", itemCode);

        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject jsonItem = (JSONObject) response;
                    int id = jsonItem.getInt("id");
                    String name = jsonItem.getString("name");
                    int price = jsonItem.getInt("price");
                    String details = jsonItem.getString("detail");

                    //     int cost = jsonItem.getInt("cost");
                    String place = jsonItem.getString("place");
                    int store_id = jsonItem.getInt("store_id");
                    int storageCount = jsonItem.getInt("count");

                    iteminfo = new Item();
                    iteminfo.setPlace(place);
                    iteminfo.setStore_id(store_id);
                    //   iteminfo.setCost(cost);
                    iteminfo.setCount(storageCount);
                    itemArrayList.add(iteminfo);


                    SellItem = new SellMenuItem();
                    SellItem.setItem_count(1);
                    SellItem.setItem_name(name);
                    SellItem.setItem_price(price);
                    SellItem.setItem_id(id);
                    //detail
                    if ((!details.equalsIgnoreCase("null")) && (details != null) && (!details.equals(""))) {
                        SellItem.setF1(details);
                        getItemDetails(details, SellItem);

                    } else {
                        menuItems.add(SellItem);

                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "لم يتم الكشف عن باركود",
                            Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "لم يتم الكشف عن باركود" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "لم يتم الكشف عن باركود", Toast.LENGTH_SHORT).show();
                hidepDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }


    private void getMenuID_Card(String url, String customer_id) {
        Map<String, String> params = new HashMap<>();
        params.put("customer_id", customer_id);

        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("karrar", response.toString());

                try {
                    JSONObject jsonMenu = (JSONObject) response;

                    sellMenuId = jsonMenu.getString("id");
                    menuIdTextView.setText(sellMenuId);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "لم يتم جلب البيانات : " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("karrar", "لاتوجد استجابة  3 " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    //as soon as item is added
    void getItemDetails(String details, final SellMenuItem SellItem) {
//        final String[] options=details.split("\\|");
        final String[] options = details.split("\\\\");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("اختر تفاصيل القطعه");


        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SellItem.setF4(options[which]);

                menuItems.add(SellItem);


                adapter.notifyDataSetChanged();

            }
        });
        builder.show();


    }

    boolean deleted = false;

    void deleteSellMenuItem(int id) {


        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                deleteItemURL, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {


                    Toast.makeText(getApplicationContext(),
                            " " + response.getString("message"), Toast.LENGTH_LONG).show();


                    if (!response.getString("message").equalsIgnoreCase("DONE"))
                        deleted = true;

//

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "لاتوجد استجابة 4  " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    deleted = true;
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"لاتوجد استجابة  5 " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                deleted = true;
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(getApplicationContext(), "You clicked row number " + position, Toast.LENGTH_SHORT).show();
        try {

            getItemInfo(view, position);

        } catch (Exception e) {

        }
//        String details=menuItems.get(position).getF1();
//        if(details!= null)
//        updateItemsDetails(details,view,position);


    }

    void getItemInfo(View view, int position) {


        PopupMenu menu = new PopupMenu(this, view);

        menu.getMenu().add(itemArrayList.get(position).getPlace() + " :المتجر ");
        menu.getMenu().add(itemArrayList.get(position).getStore_id() + " :رقم المخزن ");
        menu.getMenu().add(itemArrayList.get(position).getCount() + " :عدد القطع ");
        menu.show();

    }

    //change item details
    private void updateItemsDetails(String details, View view, int position) {
        if (!details.equalsIgnoreCase("null")) {
//            String[] options=details.split("\\|");
            String[] options = details.split("\\\\");
            PopupMenu menu = new PopupMenu(this, view);
            for (int i = 0; i < options.length; i++) {

                menu.getMenu().add(options[i]);

            }

            final int pos = position;
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem popupItem) {

                    menuItems.get(pos).setF4(popupItem.getTitle().toString());
                    adapter.notifyItemChanged(pos);
                    return true;
                }
            });

            menu.show();


        }
//

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyRecyclerViewAdapter.ViewHolder) {
            // get the removed SellItem name to display it in snack bar
            String name = menuItems.get(viewHolder.getAdapterPosition()).getItem_name();

            //delete selll menu item from db
            boolean bool = menuItems.get(viewHolder.getAdapterPosition()).getId() != null;
            if (bool) {

                deleteSellMenuItem(menuItems.get(viewHolder.getAdapterPosition()).getId());

            }


            // backup of removed SellItem for undo purpose
            final SellMenuItem deletedItem = menuItems.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the SellItem from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            //delete selll menu item from db
            if ((bool) && (deleted)) {
                adapter.restoreItem(deletedItem, deletedIndex);
                deleted = false;
            }

        }
    }

    private void setAddBtnVisibile() {
        if (menuIdTextView.getText().toString().equals("0")) {
            additem.setVisibility(View.INVISIBLE);
        } else {
            additem.setVisibility(View.VISIBLE);
        }
    }

    //
    String barcode;
    String itemCode;

    @Override
    public synchronized void onResume() {
        super.onResume();



        barcode = session.getshared("Barcode");
//        Toast.makeText(this,"/"+barcode,Toast.LENGTH_SHORT).show();
        Intent i = getIntent() ;
        Integer menu_id = i.getIntExtra("menu_id",0);
        if(menu_id != 0)
        {

            barcode = menu_id.toString();
            i.putExtra("menu_id",0);
            session.setScanfor("1");

        }

        String val = session.getshared("scanfor");

        if (val != null) {
            if (val.equals("1")) {
                //barcode is for menu
                sellMenuId = barcode;
                menuIdTextView = findViewById(R.id.textView_sellMenuId);
                menuIdTextView.setText(barcode);
                getSellMenuItemsArray();
                session.setScanfor("0");

            } else if (val.equals("2")) {
                //barcode is for SellItem
                itemCode = barcode;
                getItemObj(getItemURL);
                session.setScanfor("0");

            } else if (val.equals("3")) {

                String Customer_id = barcode;
                getMenuID_Card(getCardURL, Customer_id);
                Log.d("karrar", "getMenuID_Card func " + Customer_id);
                session.setScanfor("0");

            }


        }


        if (mService != null) {

            if (mService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth services
                mService.start();
            }
        }


    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if (DEBUG)
            Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG)
            Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth services
        if (mService != null)
            mService.stop();
        if (DEBUG)
            Log.e(TAG, "--- ON DESTROY ---");
    }

    /*****************************************************************************************************/
    private void KeyListenerInit() {

        editText = (EditText) findViewById(R.id.edit_text_out);

        sendButton = (Button) findViewById(R.id.Send_Button);
        sendButton.setOnClickListener(this);

        saveButton = (Button) findViewById(R.id.save_Button);
        saveButton.setOnClickListener(this);

        testButton = (Button) findViewById(R.id.btn_test);
        testButton.setOnClickListener(this);

        printbmpButton = (Button) findViewById(R.id.btn_printpicture);
        printbmpButton.setOnClickListener(this);

        btnScanButton = (Button) findViewById(R.id.button_scan);
        btnScanButton.setOnClickListener(this);

        hexBox = (CheckBox) findViewById(R.id.checkBoxHEX);
        hexBox.setOnClickListener(this);

        width_58mm = (RadioButton) findViewById(R.id.width_58mm);
        width_58mm.setOnClickListener(this);

        width_80 = (RadioButton) findViewById(R.id.width_80mm);
        width_80.setOnClickListener(this);

        imageViewPicture = (ImageView) findViewById(R.id.imageViewPictureUSB);
        imageViewPicture.setOnClickListener(this);

        btnClose = (Button) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(this);

        btn_BMP = (Button) findViewById(R.id.btn_prtbmp);
        btn_BMP.setOnClickListener(this);

        btn_ChoseCommand = (Button) findViewById(R.id.btn_prtcommand);
        btn_ChoseCommand.setOnClickListener(this);

        btn_prtsma = (Button) findViewById(R.id.btn_prtsma);
        btn_prtsma.setOnClickListener(this);

        btn_prttableButton = (Button) findViewById(R.id.btn_prttable);
        btn_prttableButton.setOnClickListener(this);

        btn_prtcodeButton = (Button) findViewById(R.id.btn_prtbarcode);
        btn_prtcodeButton.setOnClickListener(this);

        btn_camer = (Button) findViewById(R.id.btn_dyca);
        btn_camer.setOnClickListener(this);

        btn_scqrcode = (Button) findViewById(R.id.btn_scqr);
        btn_scqrcode.setOnClickListener(this);

        Simplified = (RadioButton) findViewById(R.id.gbk12);
        Simplified.setOnClickListener(this);
        big5 = (RadioButton) findViewById(R.id.big5);
        big5.setOnClickListener(this);
        thai = (RadioButton) findViewById(R.id.thai);
        thai.setOnClickListener(this);
        Korean = (RadioButton) findViewById(R.id.kor);
        Korean.setOnClickListener(this);

        Bitmap bm = getImageFromAssetsFile("demo.bmp");
        if (null != bm) {
            imageViewPicture.setImageBitmap(bm);
        }

        editText.setEnabled(false);
        imageViewPicture.setEnabled(false);
        width_58mm.setEnabled(false);
        width_80.setEnabled(false);
        hexBox.setEnabled(false);
        sendButton.setEnabled(false);

        testButton.setEnabled(false);
        printbmpButton.setEnabled(false);
        btnClose.setEnabled(false);
        btn_BMP.setEnabled(false);
        btn_ChoseCommand.setEnabled(false);
        btn_prtcodeButton.setEnabled(false);
        btn_prtsma.setEnabled(false);
        btn_prttableButton.setEnabled(false);
        btn_camer.setEnabled(false);
        btn_scqrcode.setEnabled(false);
        Simplified.setEnabled(false);
        Korean.setEnabled(false);
        big5.setEnabled(false);
        thai.setEnabled(false);

        mService = new BluetoothService(this, mHandler);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button_scan: {
                Intent serverIntent = new Intent(Main_Activity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;
            }
            case R.id.btn_close: {
                mService.stop();
                editText.setEnabled(false);
                imageViewPicture.setEnabled(false);
                width_58mm.setEnabled(false);
                width_80.setEnabled(false);
                hexBox.setEnabled(false);
                sendButton.setEnabled(false);

                testButton.setEnabled(false);
                printbmpButton.setEnabled(false);
                btnClose.setEnabled(false);
                btn_BMP.setEnabled(false);
                btn_ChoseCommand.setEnabled(false);
                btn_prtcodeButton.setEnabled(false);
                btn_prtsma.setEnabled(false);
                btn_prttableButton.setEnabled(false);
                btn_camer.setEnabled(false);
                btn_scqrcode.setEnabled(false);
                btnScanButton.setEnabled(true);
                Simplified.setEnabled(false);
                Korean.setEnabled(false);
                big5.setEnabled(false);
                thai.setEnabled(false);
                btnScanButton.setText(getText(R.string.connect));
                break;
            }
            case R.id.btn_test: {
                BluetoothPrintTest();
                ;
                break;
            }
            case R.id.Send_Button: {
                if (menuIdTextView.getText().equals("") || menuIdTextView.getText() == null || menuIdTextView.getText().equals("0")) {
                    Toast.makeText(getBaseContext(), "لاتوجد قائمة", Toast.LENGTH_LONG).show();
                    return;
                }

                if (btnScanButton.getText().equals("تم الاتصال")) {

                } else {
                    Toast.makeText(getBaseContext(), "يرجى الاتصال بالطابعة", Toast.LENGTH_LONG).show();
                    return;
                }
// printmenu
                sendToDB();
                String msg = msgPrintFormat();
                if (msg.length() > 0) {
                    try {
                        SharedPreferences sharedPreferences =
                                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        String hello = sharedPreferences.getString("hello", "");
                        SendDataByte(PrinterCommand.POS_Print_Text(" ", ARBIC, 22, 0, 0, 0));
                        SendDataByte(Command.ESC_Align);
                        byte[] code = PrinterCommand.getCodeBarCommand(sellMenuId, 73, 3, 140, 1, 2);
                        Command.ESC_Align[2] = 0x01;
                        SendDataByte(Command.ESC_Align);
                        SendDataByte(code);

                        SendDataByte(PrinterCommand.POS_Print_Text(hello + "\n", ARBIC, 22, 0, 0, 0));

                        SendDataByte(Command.ESC_Align);
                        SendDataByte(PrinterCommand.POS_Print_Text(msg, ARBIC, 22, 0, 0, 0));
                        SendDataByte(Command.ESC_Align);

                        SendDataByte(PrinterCommand.POS_Print_Text("\n\n\n\n\n", ARBIC, 22, 0, 0, 0));


                        String copys = sharedPreferences.getString("copys", "1");
                        if (copys.equals("2")) {
                            SendDataByte(PrinterCommand.POS_Print_Text("----------", ARBIC, 22, 2, 2, 0));

                            SendDataByte(PrinterCommand.POS_Print_Text("\n\n\n\n\n", ARBIC, 22, 0, 0, 0));
                            SendDataByte(Command.ESC_Align);

                            byte[] code3 = PrinterCommand.getCodeBarCommand(sellMenuId, 73, 3, 140, 1, 2);
                            Command.ESC_Align[2] = 0x01;
                            SendDataByte(Command.ESC_Align);
                            SendDataByte(code3);
                            SendDataByte(PrinterCommand.POS_Print_Text(hello + "\n", ARBIC, 22, 1, 1, 3));

                            SendDataByte(Command.ESC_Align);
                            SendDataByte(PrinterCommand.POS_Print_Text(msg, ARBIC, 22, 0, 0, 0));
                            SendDataByte(Command.ESC_Align);
                            SendDataByte(PrinterCommand.POS_Print_Text("\n\n\n\n\n", ARBIC, 22, 0, 0, 0));
                            SendDataByte(Command.ESC_Align);
                        }

                    } catch (Exception ex) {

                    }


                }
                clearItemData();
                break;
            }
            case R.id.save_Button: {
                if (menuIdTextView.getText().equals("") || menuIdTextView.getText() == null || menuIdTextView.getText().equals("0")) {
                    Toast.makeText(getBaseContext(), "لاتوجد قائمة", Toast.LENGTH_LONG).show();
                    return;
                }


// printmenu
                try {
                    sendToDB();

                    clearItemData();
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), "لم يتم الحفظ" + ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                break;
            }
            case R.id.width_58mm:
            case R.id.width_80mm: {
                is58mm = v == width_58mm;
                width_58mm.setChecked(is58mm);
                width_80.setChecked(!is58mm);
                break;
            }
            case R.id.btn_printpicture: {
                GraphicalPrint();
                break;
            }
            case R.id.imageViewPictureUSB: {
                Intent loadpicture = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(loadpicture, REQUEST_CHOSE_BMP);
                break;
            }
            case R.id.btn_prtbmp: {
                Print_BMP();
                break;
            }
            case R.id.btn_prtcommand: {
                CommandTest();
                break;
            }
            case R.id.btn_prtsma: {
                SendDataByte(Command.ESC_Init);
                SendDataByte(Command.LF);
                Print_Ex();
                break;
            }
            case R.id.btn_prttable: {
                SendDataByte(Command.ESC_Init);
                SendDataByte(Command.LF);
                PrintTable();
                break;
            }
            case R.id.btn_prtbarcode: {
                printBarCode();
                break;
            }
            case R.id.btn_scqr: {
                createImage();
                break;
            }
            case R.id.btn_dyca: {
                dispatchTakePictureIntent(REQUEST_CAMER);
                break;
            }
            default:
                break;
        }
    }

    /*****************************************************************************************************/
    /*
     * SendDataString
     */
    private void SendDataString(String data) {

        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (data.length() > 0) {
            try {
                mService.write(data.getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /*
     *SendDataByte
     */
    private void SendDataByte(byte[] data) {

        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        mService.write(data);

    }

    /****************************************************************************************************/
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (DEBUG)
                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
//					mTitle.setText(R.string.title_connected_to);
//					mTitle.append(mConnectedDeviceName);
                            btnScanButton.setText("تم الاتصال");
                            btnScanButton.setBackground(null);
                            Print_Test();//
                            btnScanButton.setEnabled(false);
                            editText.setEnabled(true);
                            imageViewPicture.setEnabled(true);
                            width_58mm.setEnabled(true);
                            width_80.setEnabled(true);
                            hexBox.setEnabled(true);
                            sendButton.setEnabled(true);

                            testButton.setEnabled(true);
                            printbmpButton.setEnabled(true);
                            btnClose.setEnabled(true);
                            btn_BMP.setEnabled(true);
                            btn_ChoseCommand.setEnabled(true);
                            btn_prtcodeButton.setEnabled(true);
                            btn_prtsma.setEnabled(true);
                            btn_prttableButton.setEnabled(true);
                            btn_camer.setEnabled(true);
                            btn_scqrcode.setEnabled(true);
                            Simplified.setEnabled(true);
                            Korean.setEnabled(true);
                            big5.setEnabled(true);
                            thai.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:
//					mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            //		mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:

                    break;
                case MESSAGE_READ:

                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    editText.setEnabled(false);
                    imageViewPicture.setEnabled(false);
                    width_58mm.setEnabled(false);
                    width_80.setEnabled(false);
                    hexBox.setEnabled(false);
                    sendButton.setEnabled(false);

                    testButton.setEnabled(false);
                    printbmpButton.setEnabled(false);
                    btnClose.setEnabled(false);
                    btn_BMP.setEnabled(false);
                    btn_ChoseCommand.setEnabled(false);
                    btn_prtcodeButton.setEnabled(false);
                    btn_prtsma.setEnabled(false);
                    btn_prttableButton.setEnabled(false);
                    btn_camer.setEnabled(false);
                    btn_scqrcode.setEnabled(false);
                    Simplified.setEnabled(false);
                    Korean.setEnabled(false);
                    big5.setEnabled(false);
                    thai.setEnabled(false);
                    break;
                case MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (DEBUG)
            Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE: {
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
                        BluetoothDevice device = mBluetoothAdapter
                                .getRemoteDevice(address);
                        // Attempt to connect to the device
                        mService.connect(device);
                    }
                }
                break;
            }
            case REQUEST_ENABLE_BT: {
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session
                    KeyListenerInit();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
            case REQUEST_CHOSE_BMP: {
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaColumns.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(picturePath, opts);
                    opts.inJustDecodeBounds = false;
                    if (opts.outWidth > 1200) {
                        opts.inSampleSize = opts.outWidth / 1200;
                    }
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath, opts);
                    if (null != bitmap) {
                        imageViewPicture.setImageBitmap(bitmap);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.msg_statev1), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CAMER: {
                if (resultCode == Activity.RESULT_OK) {
                    handleSmallCameraPhoto(data);
                } else {
                    Toast.makeText(this, getText(R.string.camer), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

/****************************************************************************************************/
    /**
     * 连接成功后打印测试页
     */
    private void Print_Test() {
        String lang = getString(R.string.strLang);
        if ((lang.compareTo("en")) == 0) {
            String msg = "Congratulations!\n\n";
            String data = "You have sucessfulY";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, CHINESE, 0, 1, 1, 0));
            SendDataByte(PrinterCommand.POS_Print_Text(data, CHINESE, 0, 0, 0, 0));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        } else if ((lang.compareTo("cn")) == 0) {
            String msg = "恭喜您!\n\n";
            String data = "您已经成功的连接上了我们的便携式蓝牙打印机！\n我们公司是一家专业从事研发，生产，销售商用票据打印机和条码扫描设备于一体的高科技企业.\n\n\n\n\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, CHINESE, 0, 1, 1, 0));
            SendDataByte(PrinterCommand.POS_Print_Text(data, CHINESE, 0, 0, 0, 0));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        } else if ((lang.compareTo("hk")) == 0) {
            String msg = "恭喜您!\n";
            String data = "您已經成功的連接上了我們的便攜式藍牙打印機！ \n我們公司是一家專業從事研發，生產，銷售商用票據打印機和條碼掃描設備於一體的高科技企業.\n\n\n\n\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, BIG5, 0, 1, 1, 0));
            SendDataByte(PrinterCommand.POS_Print_Text(data, BIG5, 0, 0, 0, 0));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        } else if ((lang.compareTo("kor")) == 0) {
            String msg = "축하 해요!\n";
            String data = "성공적으로 우리의 휴대용 블루투스 프린터에 연결 한! \n우리는 하이테크 기업 중 하나에서 개발, 생산 및 상업 영수증 프린터와 바코드 스캐닝 장비 판매 전문 회사입니다.\n\n\n\n\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, KOREAN, 0, 1, 1, 0));
            SendDataByte(PrinterCommand.POS_Print_Text(data, KOREAN, 0, 0, 0, 0));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        } else if ((lang.compareTo("thai")) == 0) {
            String msg = "ขอแสดงความยินดี!\n";
            String data = "คุณได้เชื่อมต่อกับบลูทู ธ เครื่องพิมพ์แบบพกพาของเรา! \n เราเป็น บริษัท ที่มีความเชี่ยวชาญในการพัฒนา, การผลิตและการขายของเครื่องพิมพ์ใบเสร็จรับเงินและการสแกนบาร์โค้ดอุปกรณ์เชิงพาณิชย์ในหนึ่งในองค์กรที่มีเทคโนโลยีสูง.\n\n\n\n\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, THAI, 255, 1, 1, 0));
            SendDataByte(PrinterCommand.POS_Print_Text(data, THAI, 255, 0, 0, 0));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        }
    }

    /**
     * 打印测试页
     * //	 * @param mPrinter
     */
    private void BluetoothPrintTest() {
        String msg = "";
        String lang = getString(R.string.strLang);
        if ((lang.compareTo("en")) == 0) {
            msg = "Division I is a research and development, production and services in one high-tech research and development, production-oriented enterprises, specializing in POS terminals finance, retail, restaurants, bars, songs and other areas, computer terminals, self-service terminal peripheral equipment R & D, manufacturing and sales! \n company's organizational structure concise and practical, pragmatic style of rigorous, efficient operation. Integrity, dedication, unity, and efficient is the company's corporate philosophy, and constantly strive for today, vibrant, the company will be strong scientific and technological strength, eternal spirit of entrepreneurship, the pioneering and innovative attitude, confidence towards the international information industry, with friends to create brilliant information industry !!! \n\n\n";
            SendDataString(msg);
        } else if ((lang.compareTo("cn")) == 0) {
            msg = "我司是一家集科研开发、生产经营和服务于一体的高技术研发、生产型企业，专业从事金融、商业零售、餐饮、酒吧、歌吧等领域的POS终端、计算机终端、自助终端周边配套设备的研发、制造及销售！\n公司的组织机构简练实用，作风务实严谨，运行高效。诚信、敬业、团结、高效是公司的企业理念和不断追求今天，朝气蓬勃，公司将以雄厚的科技力量，永恒的创业精神，不断开拓创新的姿态，充满信心的朝着国际化信息产业领域，与朋友们携手共创信息产业的辉煌!!!\n\n\n";
            SendDataString(msg);
        } else if ((lang.compareTo("hk")) == 0) {
            msg = "我司是一家集科研開發、生產經營和服務於一體的高技術研發、生產型企業，專業從事金融、商業零售、餐飲、酒吧、歌吧等領域的POS終端、計算機終端、自助終端周邊配套設備的研發、製造及銷售！ \n公司的組織機構簡練實用，作風務實嚴謹，運行高效。誠信、敬業、團結、高效是公司的企業理念和不斷追求今天，朝氣蓬勃，公司將以雄厚的科技力量，永恆的創業精神，不斷開拓創新的姿態，充滿信心的朝著國際化信息產業領域，與朋友們攜手共創信息產業的輝煌!!!\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, BIG5, 0, 0, 0, 0));
        } else if ((lang.compareTo("kor")) == 0) {
            msg = "부문 I는 금융, 소매, 레스토랑, 바, 노래 및 기타 분야, 컴퓨터 단말기, 셀프 서비스 터미널 주변 장치 POS 터미널을 전문으로 한 첨단 기술 연구 및 개발, 생산 지향적 인 기업의 연구 및 개발, 생산 및 서비스입니다 R & D, 제조 및 판매! \n 회사의 조직 구조의 간결하고 엄격한, 효율적인 운영의 실제, 실용적인 스타일. 무결성, 헌신, 단결, 효율적인 회사의 기업 철학이며, 지속적으로, 활기찬,이 회사는 강력한 과학 기술 강도, 기업가 정신의 영원한 정신이 될 것입니다 오늘을 위해 노력, 개척과 혁신적인 태도, 국제 정보 산업을 향해 자신감, 친구와 함께 화려한 정보 산업을 만들 수 있습니다!!!\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, KOREAN, 0, 0, 0, 0));
        } else if ((lang.compareTo("thai")) == 0) {
            msg = "ส่วนฉันคือการวิจัยและการพัฒนาการผลิตและการบริการในการวิจัยหนึ่งที่มีเทคโนโลยีสูงและการพัฒนาสถานประกอบการผลิตที่มุ่งเน้นความเชี่ยวชาญในขั้ว POS การเงิน, ค้าปลีก, ร้านอาหาร, บาร์, เพลงและพื้นที่อื่น ๆ , เครื่องคอมพิวเตอร์, บริการตนเองขั้วอุปกรณ์ต่อพ่วง R & D, การผลิตและยอดขาย! \n กระชับโครงสร้าง บริษัท  ขององค์กรและการปฏิบัติในทางปฏิบัติของสไตล์อย่างเข้มงวดดำเนินงานมีประสิทธิภาพ ความซื่อสัตย์ทุ่มเทความสามัคคีและมีประสิทธิภาพคือปรัชญาขององค์กรของ บริษัท อย่างต่อเนื่องและมุ่งมั่นเพื่อวันนี้ที่สดใสของ บริษัท จะมีกำลังแรงขึ้นทางวิทยาศาสตร์และเทคโนโลยีที่แข็งแกร่งจิตวิญญาณนิรันดร์ของผู้ประกอบการที่มีทัศนคติที่เป็นผู้บุกเบิกและนวัตกรรมความเชื่อมั่นที่มีต่ออุตสาหกรรมข้อมูลระหว่างประเทศ กับเพื่อน ๆ ในการสร้างอุตสาหกรรมข้อมูลที่ยอดเยี่ยม!!!\n\n\n";
            SendDataByte(PrinterCommand.POS_Print_Text(msg, THAI, 255, 0, 0, 0));
        }
    }

    /*
     * 打印图片
     */
    private void Print_BMP() {

        //	byte[] buffer = PrinterCommand.POS_Set_PrtInit();
        Bitmap mBitmap = ((BitmapDrawable) imageViewPicture.getDrawable())
                .getBitmap();
        int nMode = 0;
        int nPaperWidth = 384;
        if (width_58mm.isChecked())
            nPaperWidth = 384;
        else if (width_80.isChecked())
            nPaperWidth = 576;
        if (mBitmap != null) {
            /**
             * Parameters:
             * mBitmap  要打印的图片
             * nWidth   打印宽度（58和80）
             * nMode    打印模式
             * Returns: byte[]
             */
            byte[] data = PrintPicture.POS_PrintBMP(mBitmap, nPaperWidth, nMode);
            //	SendDataByte(buffer);
            SendDataByte(Command.ESC_Init);
            SendDataByte(Command.LF);
            SendDataByte(data);
            SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(30));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        }
    }

    /**
     * 打印自定义表格
     */
    @SuppressLint("SimpleDateFormat")
    private void PrintTable() {

        String lang = getString(R.string.strLang);
        if ((lang.compareTo("cn")) == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String date = str + "\n\n\n\n\n\n";
            if (is58mm) {

                Command.ESC_Align[2] = 0x02;
                byte[][] allbuf;
                try {
                    allbuf = new byte[][]{

                            Command.ESC_Init, Command.ESC_Three,
                            String.format("┏━━┳━━━┳━━┳━━━━┓\n").getBytes("GBK"),
                            String.format("┃发站┃%-4s┃到站┃%-6s┃\n", "深圳", "成都").getBytes("GBK"),
                            String.format("┣━━╋━━━╋━━╋━━━━┫\n").getBytes("GBK"),
                            String.format("┃件数┃%2d/%-3d┃单号┃%-8d┃\n", 1, 222, 555).getBytes("GBK"),
                            String.format("┣━━┻┳━━┻━━┻━━━━┫\n").getBytes("GBK"),
                            String.format("┃收件人┃%-12s┃\n", "【送】测试/测试人").getBytes("GBK"),
                            String.format("┣━━━╋━━┳━━┳━━━━┫\n").getBytes("GBK"),
                            String.format("┃业务员┃%-2s┃名称┃%-6s┃\n", "测试", "深圳").getBytes("GBK"),
                            String.format("┗━━━┻━━┻━━┻━━━━┛\n").getBytes("GBK"),
                            Command.ESC_Align, "\n".getBytes("GBK")
                    };
                    byte[] buf = Other.byteArraysToBytes(allbuf);
                    SendDataByte(buf);
                    SendDataString(date);
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            } else {

                Command.ESC_Align[2] = 0x02;
                byte[][] allbuf;
                try {
                    allbuf = new byte[][]{

                            Command.ESC_Init, Command.ESC_Three,
                            String.format("┏━━┳━━━━━━━┳━━┳━━━━━━━━┓\n").getBytes("GBK"),
                            String.format("┃发站┃%-12s┃到站┃%-14s┃\n", "深圳", "成都").getBytes("GBK"),
                            String.format("┣━━╋━━━━━━━╋━━╋━━━━━━━━┫\n").getBytes("GBK"),
                            String.format("┃件数┃%6d/%-7d┃单号┃%-16d┃\n", 1, 222, 55555555).getBytes("GBK"),
                            String.format("┣━━┻┳━━━━━━┻━━┻━━━━━━━━┫\n").getBytes("GBK"),
                            String.format("┃收件人┃%-28s┃\n", "【送】测试/测试人").getBytes("GBK"),
                            String.format("┣━━━╋━━━━━━┳━━┳━━━━━━━━┫\n").getBytes("GBK"),
                            String.format("┃业务员┃%-10s┃名称┃%-14s┃\n", "测试", "深圳").getBytes("GBK"),
                            String.format("┗━━━┻━━━━━━┻━━┻━━━━━━━━┛\n").getBytes("GBK"),
                            Command.ESC_Align, "\n".getBytes("GBK")
                    };
                    byte[] buf = Other.byteArraysToBytes(allbuf);
                    SendDataByte(buf);
                    SendDataString(date);
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        } else if ((lang.compareTo("en")) == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String date = str + "\n\n\n\n\n\n";
            if (is58mm) {

                Command.ESC_Align[2] = 0x02;
                byte[][] allbuf;
                try {
                    allbuf = new byte[][]{

                            Command.ESC_Init, Command.ESC_Three,
                            String.format("┏━━┳━━━┳━━┳━━━━┓\n").getBytes("GBK"),
                            String.format("┃XXXX┃%-6s┃XXXX┃%-8s┃\n", "XXXX", "XXXX").getBytes("GBK"),
                            String.format("┣━━╋━━━╋━━╋━━━━┫\n").getBytes("GBK"),
                            String.format("┃XXXX┃%2d/%-3d┃XXXX┃%-8d┃\n", 1, 222, 555).getBytes("GBK"),
                            String.format("┣━━┻┳━━┻━━┻━━━━┫\n").getBytes("GBK"),
                            String.format("┃XXXXXX┃%-18s┃\n", "【XX】XXXX/XXXXXX").getBytes("GBK"),
                            String.format("┣━━━╋━━┳━━┳━━━━┫\n").getBytes("GBK"),
                            String.format("┃XXXXXX┃%-2s┃XXXX┃%-8s┃\n", "XXXX", "XXXX").getBytes("GBK"),
                            String.format("┗━━━┻━━┻━━┻━━━━┛\n").getBytes("GBK"),
                            Command.ESC_Align, "\n".getBytes("GBK")
                    };
                    byte[] buf = Other.byteArraysToBytes(allbuf);
                    SendDataByte(buf);
                    SendDataString(date);
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            } else {

                Command.ESC_Align[2] = 0x02;
                byte[][] allbuf;
                try {
                    allbuf = new byte[][]{

                            Command.ESC_Init, Command.ESC_Three,
                            String.format("┏━━┳━━━━━━━┳━━┳━━━━━━━━┓\n").getBytes("GBK"),
                            String.format("┃XXXX┃%-14s┃XXXX┃%-16s┃\n", "XXXX", "XXXX").getBytes("GBK"),
                            String.format("┣━━╋━━━━━━━╋━━╋━━━━━━━━┫\n").getBytes("GBK"),
                            String.format("┃XXXX┃%6d/%-7d┃XXXX┃%-16d┃\n", 1, 222, 55555555).getBytes("GBK"),
                            String.format("┣━━┻┳━━━━━━┻━━┻━━━━━━━━┫\n").getBytes("GBK"),
                            String.format("┃XXXXXX┃%-34s┃\n", "【XX】XXXX/XXXXXX").getBytes("GBK"),
                            String.format("┣━━━╋━━━━━━┳━━┳━━━━━━━━┫\n").getBytes("GBK"),
                            String.format("┃XXXXXX┃%-12s┃XXXX┃%-16s┃\n", "XXXX", "XXXX").getBytes("GBK"),
                            String.format("┗━━━┻━━━━━━┻━━┻━━━━━━━━┛\n").getBytes("GBK"),
                            Command.ESC_Align, "\n".getBytes("GBK")
                    };
                    byte[] buf = Other.byteArraysToBytes(allbuf);
                    SendDataByte(buf);
                    SendDataString(date);
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印自定义小票
     */
    @SuppressLint("SimpleDateFormat")
    private void Print_Ex() {

        String lang = getString(R.string.strLang);
        if ((lang.compareTo("ar")) == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String date = str + "\n\n\n\n\n\n";
            if (is58mm) {

                try {
                    byte[] qrcode = PrinterCommand.getBarCommand("karrar", 0, 3, 6);//
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    SendDataByte(qrcode);

                    SendDataByte(Command.ESC_Align);
//				Command.GS_ExclamationMark[2] = 0x11;
//				SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("كرار حساني".getBytes("ISO-8859-6"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("门店号: 888888\n单据  S00003333\n收银员：1001\n单据日期：xxxx-xx-xx\n打印时间：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
//				SendDataByte("品名       数量    单价    金额\nNIKE跑鞋   10.00   899     8990\nNIKE篮球鞋 10.00   1599    15990\n".getBytes("GBK"));
//				Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("(以上信息为测试模板,如有苟同，纯属巧合!)\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x02;
                    SendDataByte(Command.ESC_Align);
                    SendDataString(date);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
                    SendDataByte(Command.GS_V_m_n);

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                try {
                    byte[] qrcode = PrinterCommand.getBarCommand("热敏打印机!", 0, 3, 6);
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    SendDataByte(qrcode);

                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("NIKE专卖店\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("门店号: 888888\n单据  S00003333\n收银员：1001\n单据日期：xxxx-xx-xx\n打印时间：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
                    SendDataByte("品名            数量    单价    金额\nNIKE跑鞋        10.00   899     8990\nNIKE篮球鞋      10.00   1599    15990\n".getBytes("GBK"));
                    SendDataByte("数量：                20.00\n总计：                16889.00\n付款：                17000.00\n找零：                111.00\n".getBytes("GBK"));
                    SendDataByte("公司名称：NIKE\n公司网址：www.xxx.xxx\n地址：深圳市xx区xx号\n电话：0755-11111111\n服务专线：400-xxx-xxxx\n===========================================\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("(以上信息为测试模板,如有苟同，纯属巧合!)\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x02;
                    SendDataByte(Command.ESC_Align);
                    SendDataString(date);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if ((lang.compareTo("en")) == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String date = str + "\n\n\n\n\n\n";
            if (is58mm) {

                try {
                    byte[] qrcode = PrinterCommand.getBarCommand("Zijiang Electronic Thermal Receipt Printer!", 0, 3, 6);//
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    SendDataByte(qrcode);

                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("NIKE Shop\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("Number:  888888\nReceipt  S00003333\nCashier：1001\nDate：xxxx-xx-xx\nPrint Time：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
                    SendDataByte("Name    Quantity    price  Money\nShoes   10.00       899     8990\nBall    10.00       1599    15990\n".getBytes("GBK"));
                    SendDataByte("Quantity：             20.00\ntotal：                16889.00\npayment：              17000.00\nKeep the change：      111.00\n".getBytes("GBK"));
                    SendDataByte("company name：NIKE\nSite：www.xxx.xxx\naddress：ShenzhenxxAreaxxnumber\nphone number：0755-11111111\nHelpline：400-xxx-xxxx\n================================\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("Welcome again!\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);

                    SendDataByte("(The above information is for testing template, if agree, is purely coincidental!)\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x02;
                    SendDataByte(Command.ESC_Align);
                    SendDataString(date);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                try {
                    byte[] qrcode = PrinterCommand.getBarCommand("Zijiang Electronic Thermal Receipt Printer!", 0, 3, 8);
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    SendDataByte(qrcode);

                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("NIKE Shop\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("Number: 888888\nReceipt  S00003333\nCashier：1001\nDate：xxxx-xx-xx\nPrint Time：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
                    SendDataByte("Name                    Quantity price  Money\nNIKErunning shoes        10.00   899     8990\nNIKEBasketball Shoes     10.00   1599    15990\n".getBytes("GBK"));
                    SendDataByte("Quantity：               20.00\ntotal：                  16889.00\npayment：                17000.00\nKeep the change：                111.00\n".getBytes("GBK"));
                    SendDataByte("company name：NIKE\nSite：www.xxx.xxx\naddress：shenzhenxxAreaxxnumber\nphone number：0755-11111111\nHelpline：400-xxx-xxxx\n================================================\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("Welcome again!\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("(The above information is for testing template, if agree, is purely coincidental!)\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x02;
                    SendDataByte(Command.ESC_Align);
                    SendDataString(date);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(48));
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印条码、二维码
     */
    private void printBarCode() {

        new AlertDialog.Builder(Main_Activity.this).setTitle(getText(R.string.btn_prtcode))
                .setItems(codebar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SendDataByte(byteCodebar[which]);
                        //	String str = editText.getText().toString();
                        String str = "k199900";
                        if (which == 0) {
                            if (str.length() == 11 || str.length() == 12) {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 65, 3, 168, 0, 2);
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataString("UPC_A\n");
                                SendDataByte(code);
                            } else {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (which == 1) {
                            if (str.length() == 6 || str.length() == 7) {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 66, 3, 168, 0, 2);
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataString("UPC_E\n");
                                SendDataByte(code);
                            } else {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (which == 2) {
                            if (str.length() == 12 || str.length() == 13) {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 67, 3, 168, 0, 2);
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataString("JAN13(EAN13)\n");
                                SendDataByte(code);
                            } else {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (which == 3) {
                            if (str.length() > 0) {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 68, 3, 168, 0, 2);
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataString("JAN8(EAN8)\n");
                                SendDataByte(code);
                            } else {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (which == 4) {
                            if (str.length() == 0) {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 69, 3, 168, 1, 2);
                                SendDataString("CODE39\n");
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataByte(code);
                            }
                        } else if (which == 5) {
                            if (str.length() == 0) {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 70, 3, 168, 1, 2);
                                SendDataString("ITF\n");
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataByte(code);
                            }
                        } else if (which == 6) {
                            if (str.length() == 0) {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 71, 3, 168, 1, 2);
                                SendDataString("CODABAR\n");
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataByte(code);
                            }
                        } else if (which == 7) {
                            if (str.length() == 0) {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 72, 3, 168, 1, 2);
                                SendDataString("CODE93\n");
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataByte(code);
                            }
                        } else if (which == 8) {
                            if (str.length() == 0) {
                                Toast.makeText(Main_Activity.this, getText(R.string.msg_error), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                byte[] code = PrinterCommand.getCodeBarCommand(str, 73, 3, 168, 1, 2);
                                SendDataString("CODE128\n");
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataByte(code);
                            }
                        } else if (which == 9) {
                            if (str.length() == 0) {
                                Toast.makeText(Main_Activity.this, getText(R.string.empty1), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                byte[] code = PrinterCommand.getBarCommand(str, 1, 3, 8);
                                SendDataString("QR Code\n");
                                SendDataByte(new byte[]{0x1b, 0x61, 0x00});
                                SendDataByte(code);
                            }
                        }
                    }
                }).create().show();
    }

    /**
     * public static Bitmap createAppIconText(Bitmap icon, String txt, boolean is58mm, int hight)
     * Bitmap  icon     源图
     * String txt       要转换的字符串
     * boolean is58mm   打印宽度(58和80)
     * int hight        转换后的图片高度
     */
    private void GraphicalPrint() {

        String txt_msg = editText.getText().toString();
        if (txt_msg.length() == 0) {
            Toast.makeText(Main_Activity.this, getText(R.string.empty1), Toast.LENGTH_SHORT).show();
            return;
        } else {
            Bitmap bm1 = getImageFromAssetsFile("demo.jpg");
            if (width_58mm.isChecked()) {

                Bitmap bmp = Other.createAppIconText(bm1, txt_msg, 25, is58mm, 200);
                int nMode = 0;
                int nPaperWidth = 384;

                if (bmp != null) {
                    byte[] data = PrintPicture.POS_PrintBMP(bmp, nPaperWidth, nMode);
                    SendDataByte(Command.ESC_Init);
                    SendDataByte(Command.LF);
                    SendDataByte(data);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(30));
                    SendDataByte(PrinterCommand.POS_Set_Cut(1));
                    SendDataByte(PrinterCommand.POS_Set_PrtInit());
                }
            } else if (width_80.isChecked()) {
                Bitmap bmp = Other.createAppIconText(bm1, txt_msg, 25, false, 200);
                int nMode = 0;

                int nPaperWidth = 576;
                if (bmp != null) {
                    byte[] data = PrintPicture.POS_PrintBMP(bmp, nPaperWidth, nMode);
                    SendDataByte(Command.ESC_Init);
                    SendDataByte(Command.LF);
                    SendDataByte(data);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(30));
                    SendDataByte(PrinterCommand.POS_Set_Cut(1));
                    SendDataByte(PrinterCommand.POS_Set_PrtInit());
                }
            }
        }
    }

    /**
     * 打印指令测试
     */
    private void CommandTest() {

        String lang = getString(R.string.strLang);
        if ((lang.compareTo("cn")) == 0) {
            new AlertDialog.Builder(Main_Activity.this).setTitle(getText(R.string.chosecommand))
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SendDataByte(byteCommands[which]);
                            try {
                                if (which == 16 || which == 17 || which == 18 || which == 19 || which == 22
                                        || which == 23 || which == 24 || which == 0 || which == 1 || which == 27) {
                                    return;
                                } else {
                                    SendDataByte("热敏票据打印机ABCDEFGabcdefg123456,.;'/[{}]!\n热敏票据打印机ABCDEFGabcdefg123456,.;'/[{}]!\n热敏票据打印机ABCDEFGabcdefg123456,.;'/[{}]!\n热敏票据打印机ABCDEFGabcdefg123456,.;'/[{}]!\n热敏票据打印机ABCDEFGabcdefg123456,.;'/[{}]!\n热敏票据打印机ABCDEFGabcdefg123456,.;'/[{}]!\n".getBytes("GBK"));
                                }

                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).create().show();
        } else if ((lang.compareTo("en")) == 0) {
            new AlertDialog.Builder(Main_Activity.this).setTitle(getText(R.string.chosecommand))
                    .setItems(itemsen, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SendDataByte(byteCommands[which]);
                            try {
                                if (which == 16 || which == 17 || which == 18 || which == 19 || which == 22
                                        || which == 23 || which == 24 || which == 0 || which == 1 || which == 27) {
                                    return;
                                } else {
                                    SendDataByte("Thermal Receipt Printer ABCDEFGabcdefg123456,.;'/[{}]!\nThermal Receipt PrinterABCDEFGabcdefg123456,.;'/[{}]!\nThermal Receipt PrinterABCDEFGabcdefg123456,.;'/[{}]!\nThermal Receipt PrinterABCDEFGabcdefg123456,.;'/[{}]!\nThermal Receipt PrinterABCDEFGabcdefg123456,.;'/[{}]!\nThermal Receipt PrinterABCDEFGabcdefg123456,.;'/[{}]!\n".getBytes("GBK"));
                                }

                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).create().show();
        }
    }

    /************************************************************************************************/
    /*
     * 生成QR图
     */
    private void createImage() {
        try {
            // 需要引入zxing包
            QRCodeWriter writer = new QRCodeWriter();

            String text = editText.getText().toString();

            Log.i(TAG, "生成的文本：" + text);
            if (text == null || "".equals(text) || text.length() < 1) {
                Toast.makeText(this, getText(R.string.empty), Toast.LENGTH_SHORT).show();
                return;
            }

            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:"
                    + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }

            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            byte[] data = PrintPicture.POS_PrintBMP(bitmap, 384, 0);
            SendDataByte(data);
            SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(30));
            SendDataByte(PrinterCommand.POS_Set_Cut(1));
            SendDataByte(PrinterCommand.POS_Set_PrtInit());
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    //************************************************************************************************//
    /*
     * 调用系统相机
     */
    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        imageViewPicture.setImageBitmap(mImageBitmap);
    }
/****************************************************************************************************/
    /**
     * 加载assets文件资源
     */
    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }






















}