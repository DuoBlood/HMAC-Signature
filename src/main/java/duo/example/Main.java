package duo.example;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import duo.example.ui.SuiteTab;

public class Main implements BurpExtension {

    public static final String NAME = "xSignature (v1.0.2)";

    @Override
    public void initialize(MontoyaApi api) {
        api.extension().setName(NAME);
        final SuiteTab ui = new SuiteTab();

        api.userInterface().registerSuiteTab("xSignature", ui);
        api.http().registerHttpHandler(new MyHttpHandler(new SignatureUtil(), ui));
    }
}