package com.example.promise;

/**
 * Created by Yeohwankyoo on 2016-06-13.
 */


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.StringTokenizer;

/**
 * Created by Yeohwankyoo on 2016-06-11.
 */
public class CallFriendList extends AsyncTask<String, Integer, String> {

    //Activity mActivity;
    MainActivity context;
    ProgressDialog dialog;
    final ArrayList<String> sitemName = new ArrayList<String>();
    final ArrayList<String> sitemID = new ArrayList<String>();

    ArrayList<String> pitemName = new ArrayList<String>();  // 메인 엑티비티에서 가져온 휴대폰의 연락처
    ArrayList<String> pitemPhone = new ArrayList<String>(); //메인 엑티비티에서 가져온 휴대폰의 연락처
    LoadManager load;//접속과 요청을 담당하는 객체 선언

    public CallFriendList(Context context) {
        this.context = (MainActivity)context;

        load = new LoadManager();

    }

    //pI.item 받아오기
    public CallFriendList(Context context, ArrayList<String> nameList, ArrayList<String> phoneList)
    {
        this.context = (MainActivity)context;

        pitemName = nameList;    //여기서 초기화 해준다
        pitemPhone = phoneList;  //여기서 초기화!
        load = new LoadManager();

    }

    //백그라운드 작업 수행전에 해야할 업무등을 이 메서드에 작성하며 되는데,
    //이 메서드는 UI쓰레드에 의해 작동하므로 UI를 제어할 수 있다.
    //따라서 이 타이밍에 진행바를 보여주는 작업등을 할 수 있다.
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        //dialog.setCancelable(false);
        dialog.show();
    }

    //비동기방식으로 작동할 메서드이며, 주로 메인쓰레드와는 별도로
    //웹사이트의 연동이나 지연이 발생하는 용도로 사용하면 된다.
    //사실상 개발자가 정의 쓰레드에서 run메서드와 비슷하다
    //  'String...' 가변형 파라미터로 파라미터 개수 상관없이 넣을 수 있다.
    protected String doInBackground(String... params) {

        //웹서버에 요청시도
        String data = load.request();

        return data;
    }


    //백그라운드 메서드가 업무수행을 마칠때 호출되는 메서드.
    //UI쓰레드에 의해 호출되므로, UI쓰레드를 제어할 수 있다.
    //따라서 진행바를 그만 나오게 할 수 있다.
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        dialog.dismiss();
        //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        //System.out.println("a");
        //최종적으로 웹서버로부터 데이터를 완전히 가져온 시점은 이 메서드이므로,
        //어댑터가 보유한 ArrayList를 갱신시켜주자!
        //ArrayList<DataVo> dataList = context.adapter.lst;
        //dataList.removeAll(dataList);
        //String str = "{a:[{name:'토끼1',age:22,isBool:true},{name:'토끼2',age:25,isBool:true},{name:'토끼3',age:27,isBool:true}]}";
        //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

        try {

            JSONArray array = new JSONArray(result);
            // DataVo dataVo = null;
            JSONObject obj = null;
            //System.out.println("1");
            for(int i=0;i<array.length();i++){
                obj = array.getJSONObject(i);
                //dataVo = new DataVo();

                Log.d("이름: ", obj.getString("userID"));
                Log.d("phoneNum :", obj.getString("phoneNum"));

                StringTokenizer num = new StringTokenizer(obj.getString("phoneNum"), "-");
                String tmp = "";
                while(num.hasMoreTokens())
                {
                    tmp = tmp+num.nextToken();
                }
                String number = tmp;
                String ID = obj.getString("userID");
                myFriendList(number, ID);
                //System.out.println("2obj.getString(name):" + obj.getString("name"));
                // dataVo.setName(obj.getString("name"));
                // dataVo.setUserID(obj.getString("userID"));
                //  dataVo.setEmail(obj.getString("email"));
                //dataList.add(dataVo);
                //context.list.invalidateViews();;
                //sitemID.add(obj.getString("userID"));
            }
            //System.out.println("3");
            // context.list.invalidate();

            //System.out.println("array.length():"+array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.makePhoneList();
    }
    /////////////어레이 리스트에 친구들 목록 받아와서 웹서버에서 받아온 연락처랑 비교해서 띠운다.////
    public void myFriendList(String number, String ID)
    {

        for(int i=0; i<pitemPhone.size(); i++)
        {
            if(number.equalsIgnoreCase(pitemPhone.get(i).toString()))
            {
                sitemName.add(pitemName.get(i).toString());//서버에서 받아온 전화번호와 휴대폰의 전화번호를 비교를 해서 같으면 그 이름을 저장을 한다.
                sitemID.add(ID);
               // Log.d("Server: ", "Name : " + sitemName.get(i).toString() + " / Number : " + number);
            }
        }

    }
}