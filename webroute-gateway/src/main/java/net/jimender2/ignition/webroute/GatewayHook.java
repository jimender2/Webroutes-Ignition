package net.jimender2.ignition.webroute;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LogUtil;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.ContextState;
import com.inductiveautomation.ignition.gateway.dataroutes.RouteGroup;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IRecordListener;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.components.AbstractNamedTab;
import com.inductiveautomation.ignition.gateway.web.components.ConfigPanel;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.INamedTab;
import com.inductiveautomation.ignition.gateway.web.models.KeyValue;
import com.inductiveautomation.ignition.gateway.web.pages.BasicReactPanel;
import com.inductiveautomation.ignition.gateway.web.pages.status.StatusCategories;
import com.inductiveautomation.ignition.gateway.web.pages.status.overviewmeta.OverviewContributor;
import org.apache.wicket.Application;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.protocol.http.WebApplication;
import com.inductiveautomation.ignition.gateway.dataroutes.RequestContext;
import com.inductiveautomation.ignition.gateway.dataroutes.RouteGroup;
import com.inductiveautomation.ignition.gateway.dataroutes.WicketAccessControl;

import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
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

    public JSONObject getConnectionDetail(RequestContext requestContext, HttpServletResponse httpServletResponse, String connectionName) throws JSONException, UnsupportedEncodingException {
        GatewayContext context = requestContext.getGatewayContext();
        JSONObject json = new JSONObject();
        String decodedConnectionName = URLDecoder.decode(connectionName, "UTF-8");
        json.put("connection", "Hello world from "+decodedConnectionName);
        return json;
    }

    @Override
    public void startup(LicenseState licenseState) {
        log.info("starting my gatewayhook");

        // this.routes = new RouteGroup(TYPE_JSON, "my-routes");
        // this.routes.addRoute("/connection/{connectionName}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}/{connectionDetail3}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}/{connectionDetail3}/{connectionDetail4}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}/{connectionDetail3}/{connectionDetail4}/{connectionDetail5}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}/{connectionDetail3}/{connectionDetail4}/{connectionDetail5}/{connectionDetail6}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}/{connectionDetail3}/{connectionDetail4}/{connectionDetail5}/{connectionDetail6}/{connectionDetail7}", this::getConnectionDetail);
        // this.routes.addRoute("/connection/{connectionName}/{connectionDetail}/{connectionDetail2}/{connectionDetail3}/{connectionDetail4}/{connectionDetail5}/{connectionDetail6
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
