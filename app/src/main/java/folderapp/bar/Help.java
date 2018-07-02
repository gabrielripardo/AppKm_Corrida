package folderapp.bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Help extends Fragment {
    private WebView page;

    public static Help newInstance() {
        Help fragment = new Help();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help, container, false);

        page = (WebView) v.findViewById(R.id.page_wV);
        page.setWebViewClient(new MyBrowser());
        abrirPagina();
        return v;
    }
    public void abrirPagina(){
        page.getSettings().setLoadsImagesAutomatically(true);
        page.getSettings().setJavaScriptEnabled(true);
        page.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        page.getSettings().setBuiltInZoomControls(true);
        page.loadUrl("file:///android_asset/help.html");

    }
    private class MyBrowser extends WebViewClient{
        //Pode ser substituída pela instância direta dentro do método setWebViewClient()
    }
}
