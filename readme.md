# Webroute Module

This simple module is an example of how you can mount some web routes inside of Ignition.

You can access either of the two routes that I have shown in here at either http(s)://{Your Ignition server}:{port}/data/{moduleID}/jimender2/helloworld or http(s)://{Your Ignition server}:{port}/data/{moduleID}/jimender2/:name

In this case the moduleID is net.jimender2.ignition.webroute so the full path is http(s)://{Your Ignition server}:{port}/data/net.jimender2.ignition.webroute/jimender2/helloworld or http(s)://{Your Ignition server}:{port}/data/net.jimender2.ignition.webroute/jimender2/:name


If you take a look in the code of the `mountRouteHandlers` you can see a function that does all the hard work.

	routes.newRoute("/jimender2/:name")
            .handler((req, res) -> getConnectionDetail(req, res, req.getParameter("name")))
            .type(TYPE_JSON)
            .restrict(WicketAccessControl.STATUS_SECTION)
            .mount();

The `routes.newRoute` is the real heavy lifter in this.  This defines the route that you want.  This path will always come after /data/{moduleID}

The `.handler` is the part that lets you handle the data that you recieve.  In most cases this is a lambda function that calls a function elsewhere in your module.  You can pass parameters and get them back out similar to what we do in the current handler.

The `.type` lets you return one of the following types.  This is an optional parameter.

	TYPE_IMG_SVG_XML	 
	TYPE_JSON	 
	TYPE_PLAIN_TEXT	 
	TYPE_TEXT_CSS	 
	TYPE_TEXT_HTML	 
	TYPE_XML	 
	TYPE_ZIP

The `.restrict` lets you restrict who can access this to two types of people (may be more but unsure).  It can either be people who have access to the config section of the Ignition gateway and people who have access to the status section of Ignition.  By ommitting this, anyone has access to it.

The `.mount` actually mounts the route.
	
#### Note

While this example covers a lot, this is not a full tutorial and there may be some mistakes in it.