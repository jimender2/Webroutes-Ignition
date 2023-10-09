package net.jimender2.ignition.webroute;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LogUtil;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.dataroutes.RouteGroup;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.dataroutes.RequestContext;

import com.inductiveautomation.ignition.gateway.dataroutes.WicketAccessControl;

import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import static com.inductiveautomation.ignition.gateway.dataroutes.RouteGroup.TYPE_JSON;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

public class GatewayHook extends AbstractGatewayModuleHook {
    private GatewayContext context;

    private final LoggerEx log = LogUtil.getLogger(getClass().getSimpleName());

    private RouteGroup routes;

    @Override
    public void setup(GatewayContext gatewayContext) {
        log.info("starting my gatewayhook setup");
        this.context = gatewayContext;

        log.info("Setup Complete.");
    }

    public JSONObject getConnectionDetail(RequestContext requestContext, HttpServletResponse httpServletResponse,
            String connectionName) throws JSONException, UnsupportedEncodingException {
        GatewayContext context = requestContext.getGatewayContext();
        JSONObject json = new JSONObject();
        String decodedConnectionName = URLDecoder.decode(connectionName, "UTF-8");
        json.put("connection", "Hello world from " + decodedConnectionName);
        return json;
    }

    @Override
    public void startup(LicenseState licenseState) {
        log.info("starting my gatewayhook");

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void mountRouteHandlers(RouteGroup routes) {
        log.info("mounting my routes");
        this.routes = routes;

        routes.newRoute("/jimender2/hello_world")
                .handler((request, response) -> "Hello, world!")
                .mount();

        routes.newRoute("/jimender2/:name")
                .handler((req, res) -> getConnectionDetail(req, res, req.getParameter("name")))
                .type(TYPE_JSON)
                .restrict(WicketAccessControl.STATUS_SECTION)
                .mount();

        log.info("routes mounted");

    }
}
