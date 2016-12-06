package com.dotcms.rest.api.v1.site;

import static com.dotcms.util.CollectionsUtils.list;
import static com.dotcms.util.CollectionsUtils.map;
import static com.dotcms.util.CollectionsUtils.mapAll;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.dotcms.TestBase;
import com.dotcms.repackage.javax.ws.rs.core.Response;
import com.dotcms.repackage.org.apache.commons.lang.StringUtils;
import com.dotcms.repackage.org.apache.struts.Globals;
import com.dotcms.rest.InitDataObject;
import com.dotcms.rest.ResponseEntityView;
import com.dotcms.rest.RestUtilTest;
import com.dotcms.rest.WebResource;
import com.dotcms.util.I18NUtil;
import com.dotmarketing.beans.Host;
import com.dotmarketing.business.DotStateException;
import com.dotmarketing.business.UserAPI;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.contentlet.business.HostAPI;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.WebKeys;
import com.dotmarketing.util.json.JSONException;
import com.liferay.portal.model.User;

/**
 * {@link siteResource} test
 * @author jsanca
 */
public class SiteResourceTest extends TestBase {

	private static final int page = 1;
	private static final int count = 20;
	/**
	 * Queries the list of sites associated to a user based on the value of the
	 * "filter" parameter being an actual filter or an empty value.
	 * 
	 * @throws JSONException
	 * @throws DotSecurityException
	 * @throws DotDataException
	 */
    @Test
    public void testNullAndEmptyFilter() throws JSONException, DotSecurityException, DotDataException {
        final HttpServletRequest request  = mock(HttpServletRequest.class);
        final HttpSession session  = mock(HttpSession.class);
        final HostAPI hostAPI     = mock(HostAPI.class);
        final UserAPI userAPI = mock(UserAPI.class);
        final WebResource webResource       = mock(WebResource.class);
        final ServletContext context = mock(ServletContext.class);
        final InitDataObject initDataObject = mock(InitDataObject.class);
        final User user = new User();
        final List<Host> hosts = getSites();
        
        Config.CONTEXT = context;
        Config.CONTEXT = context;

        when(initDataObject.getUser()).thenReturn(user);
        when(webResource.init(null, true, request, true, null)).thenReturn(initDataObject);
        when(hostAPI.findAll(user, Boolean.FALSE)).thenReturn(hosts);
        when(context.getInitParameter("company_id")).thenReturn(RestUtilTest.DEFAULT_COMPANY);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(Globals.LOCALE_KEY)).thenReturn(new Locale.Builder().setLanguage("en").setRegion("US").build());
        SiteResource siteResource =
                new SiteResource(webResource, new SiteHelper( hostAPI ), I18NUtil.INSTANCE, userAPI);

        Response response1 = siteResource.sites(request, null, false, page, count);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 200);
        assertNotNull(response1.getEntity());
        assertTrue(response1.getEntity() instanceof ResponseEntityView);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getErrors());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getErrors().size() == 0);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getEntity());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getEntity() instanceof Map);
        assertNotNull(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results").equals("demo.dotcms.com"));
        assertTrue(Host.class.cast(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).get(0))
                .getHostname().equals("demo.dotcms.com"));

        response1 = siteResource.sites(request, StringUtils.EMPTY, false, page, count);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 200);
        assertNotNull(response1.getEntity());
        assertTrue(response1.getEntity() instanceof ResponseEntityView);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getErrors());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getErrors().size() == 0);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getEntity());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getEntity() instanceof Map);
        assertNotNull(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results").equals("demo.dotcms.com"));
        assertTrue(Host.class.cast(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).get(0))
                .getHostname().equals("demo.dotcms.com"));

        response1 = siteResource.sites(request, "*", false, page, count);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 200);
        assertNotNull(response1.getEntity());
        assertTrue(response1.getEntity() instanceof ResponseEntityView);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getErrors());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getErrors().size() == 0);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getEntity());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getEntity() instanceof Map);
        assertNotNull(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results").equals("demo.dotcms.com"));
        assertTrue(Host.class.cast(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).get(0))
                .getHostname().equals("demo.dotcms.com"));
    }

    @Test
    public void testPreffixFilter() throws JSONException, DotSecurityException, DotDataException {
        final HttpServletRequest request  = mock(HttpServletRequest.class);
        final HttpSession session  = mock(HttpSession.class);
        final HostAPI hostAPI     = mock(HostAPI.class);
        final UserAPI userAPI = mock(UserAPI.class);
        final WebResource webResource       = mock(WebResource.class);
        final ServletContext context = mock(ServletContext.class);
        final InitDataObject initDataObject = mock(InitDataObject.class);
        final User user = new User();
        final List<Host> hosts = getThreeSites();

        Config.CONTEXT = context;
        Config.CONTEXT = context;

        when(initDataObject.getUser()).thenReturn(user);
        when(webResource.init(null, true, request, true, null)).thenReturn(initDataObject);
        when(hostAPI.findAll(user, Boolean.FALSE)).thenReturn(hosts);
        when(context.getInitParameter("company_id")).thenReturn(RestUtilTest.DEFAULT_COMPANY);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(Globals.LOCALE_KEY)).thenReturn(new Locale.Builder().setLanguage("en").setRegion("US").build());
        SiteResource siteResource =
                new SiteResource(webResource, new SiteHelper( hostAPI ), I18NUtil.INSTANCE, userAPI);

        Response response1 = siteResource.sites(request, "demo", false, page, count);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 200);
        assertNotNull(response1.getEntity());
        assertTrue(response1.getEntity() instanceof ResponseEntityView);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getErrors());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getErrors().size() == 0);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getEntity());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getEntity() instanceof Map);
        assertNotNull(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results").equals("demo.dotcms.com"));
        assertTrue(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).size() == 2);
        assertTrue(Host.class.cast(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).get(0))
                .getHostname().equals("demo.awesome.dotcms.com"));
        assertTrue(Host.class.cast(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).get(1))
                .getHostname().equals("demo.dotcms.com"));

        response1 = siteResource.sites(request, "nothing", false, page, count);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 200);
        assertNotNull(response1.getEntity());
        assertTrue(response1.getEntity() instanceof ResponseEntityView);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getErrors());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getErrors().size() == 0);
        assertNotNull(ResponseEntityView.class.cast(response1.getEntity()).getEntity());
        assertTrue(ResponseEntityView.class.cast(response1.getEntity()).getEntity() instanceof Map);
        assertNotNull(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results").equals("demo.dotcms.com"));
        assertTrue(List.class.cast(Map.class.cast(Map.class.cast(ResponseEntityView.class.cast(response1.getEntity()).getEntity()).get("sites")).get("results")).size() == 0);
    }

    @Test
    public void testSwitchNullEmptyAndInvalidFilter() throws JSONException, DotSecurityException, DotDataException {
        final HttpServletRequest request  = mock(HttpServletRequest.class);
        final HttpSession session  = mock(HttpSession.class);
        final HostAPI hostAPI     = mock(HostAPI.class);
        final UserAPI userAPI = mock(UserAPI.class);
        final WebResource webResource       = mock(WebResource.class);
        final ServletContext context = mock(ServletContext.class);
        final InitDataObject initDataObject = mock(InitDataObject.class);
        final User user = new User();
        final List<Host> hosts = getThreeSites();

        Config.CONTEXT = context;

        when(initDataObject.getUser()).thenReturn(user);
        when(webResource.init(null, true, request, true, null)).thenReturn(initDataObject);
        when(hostAPI.findAll(user, true)).thenReturn(hosts);
        when(context.getInitParameter("company_id")).thenReturn(RestUtilTest.DEFAULT_COMPANY);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(Globals.LOCALE_KEY)).thenReturn(new Locale.Builder().setLanguage("en").setRegion("US").build());
        SiteResource siteResource =
                new SiteResource(webResource, new SiteHelper( hostAPI ), I18NUtil.INSTANCE, userAPI);

        Response response1 = siteResource.switchSite(request, null);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 404);

        response1 = siteResource.switchSite(request, StringUtils.EMPTY);
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 404);

        response1 = siteResource.switchSite(request, "48190c8c-not-found-8d1a-0cd5db894797");
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 404);

        response1 = siteResource.switchSite(request, "48190c8c-42c4-46af-8d1a-0cd5db894797"); // system, should be not allowed to switch
        System.out.println(response1);
        System.out.println(response1.getEntity());

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 404);
    }

    @Test
    public void testSwitchExistingHost() throws JSONException, DotSecurityException, DotDataException {
        final HttpServletRequest request  = mock(HttpServletRequest.class);
        final HttpSession session  = mock(HttpSession.class);
        final HostAPI hostAPI     = mock(HostAPI.class);
        final UserAPI userAPI = mock(UserAPI.class);
        final WebResource webResource       = mock(WebResource.class);
        final ServletContext context = mock(ServletContext.class);
        final InitDataObject initDataObject = mock(InitDataObject.class);
        final User user = new User();
        final List<Host> hosts = getThreeSites();

        Config.CONTEXT = context;
        Map<String, Object> sessionAttributes = map(WebKeys.CONTENTLET_LAST_SEARCH, "mock mock mock mock");

        when(initDataObject.getUser()).thenReturn(user);
        when(webResource.init(null, true, request, true, null)).thenReturn(initDataObject);
        when(hostAPI.findAll(user, true)).thenReturn(hosts);
        when(context.getInitParameter("company_id")).thenReturn(RestUtilTest.DEFAULT_COMPANY);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(Globals.LOCALE_KEY)).thenReturn(new Locale.Builder().setLanguage("en").setRegion("US").build());
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                Object [] args = invocation.getArguments();
                sessionAttributes.put((String) args[0], args[1]);
                return null;
            }
        }).when(session).setAttribute(
                anyString(),
                anyObject()
        );

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                Object [] args = invocation.getArguments();
                sessionAttributes.remove((String) args[0]);
                return null;
            }
        }).when(session).removeAttribute(
                anyString()
        );

        SiteResource siteResource =
                new SiteResource(webResource, new SiteHelper( hostAPI ), I18NUtil.INSTANCE, userAPI);

        Response response1 = siteResource.switchSite(request, "48190c8c-42c4-46af-8d1a-0cd5db894798");
        System.out.println(response1);
        System.out.println(response1.getEntity());
        System.out.println(sessionAttributes);

        assertNotNull(response1);
        assertEquals(response1.getStatus(), 200);
        assertTrue(sessionAttributes.size() == 1 );
        assertTrue(!sessionAttributes.containsKey(WebKeys.CONTENTLET_LAST_SEARCH));
        assertTrue(sessionAttributes.containsKey(com.dotmarketing.util.WebKeys.CMS_SELECTED_HOST_ID));
    }

	/**
	 * Verifies the list of sites that a user has access to. Such a list is used
	 * to load the items in the Site Selector component.
	 * 
	 * @throws DotSecurityException
	 * @throws DotDataException
	 */
    @Test
    public void testCurrentSites() throws DotSecurityException, DotDataException {
        final HttpServletRequest request = RestUtilTest.getMockHttpRequest();
        final HttpSession session = request.getSession();
        RestUtilTest.initMockContext();
        final User user = new User();
        final List<Host> siteList = getSites();
        final String currentSite = siteList.get(0).getIdentifier();
        final WebResource webResource = RestUtilTest.getMockWebResource( user, request );
        
        final HostAPI hostAPI = mock(HostAPI.class);
        when( hostAPI.findAll(user, Boolean.FALSE) ).thenReturn( siteList );
        final UserAPI userAPI = mock(UserAPI.class);
        when(userAPI.loadUserById(Mockito.anyString())).thenReturn(user);
        when( session.getAttribute( WebKeys.CMS_SELECTED_HOST_ID ) )
                .thenReturn( currentSite );

        final SiteResource siteResource =
                new SiteResource(webResource, new SiteHelper( hostAPI ), I18NUtil.INSTANCE, userAPI);
        final Response response = siteResource.currentSite(request);

        RestUtilTest.verifySuccessResponse(response);
        Map<String, Object> entity = (Map<String, Object>) ((ResponseEntityView) response.getEntity()).getEntity();
        assertEquals( currentSite, entity.get("currentSite") );

        List<Map<String, String>> sites = (List<Map<String, String>>) entity.get("sites");
        assertEquals(1, sites.size());
        assertEquals(siteList.get(0).getMap(), sites.get(0));
    }

    /**
     * Returns a list of 2 mocked Sites for testing purposes.
     * 
     * @return
     */
    private List<Host> getSites() {
        return list(new Host(new Contentlet(mapAll(
                map(
                        "hostName", "demo.dotcms.com",
                        "googleMap", "AIzaSyDXvD7JA5Q8S5VgfviI8nDinAq9x5Utmu0",
                        "modDate", Integer.parseInt("125466"),
                        "aliases", "",
                        "keywords", "CMS, Web Content Management, Open Source, Java, J2EE",
                        "description", "dotCMS starter site was designed to demonstrate what you can do with dotCMS.",
                        "type", "host",
                        "title", "demo.dotcms.com",
                        "inode", "54ac9a4e-3d63-4b9a-882f-27c7ba29618f",
                        "hostname", "demo.dotcms.com"),
                map(
                        "__DOTNAME__", "demo.dotcms.com",
                        "addThis", "ra-4e02119211875e7b",
                        "disabledWYSIWYG", new Object[]{},
                        "host", "SYSTEM_HOST",
                        "lastReview", 14503,
                        "stInode", "855a2d72-f2f3-4169-8b04-ac5157c4380c",
                        "owner", "dotcms.org.1",
                        "identifier", "48190c8c-42c4-46af-8d1a-0cd5db894797",
                        "runDashboard", false,
                        "languageId", 1

                ),
                map(
                        "isDefault", true,
                        "folder", "SYSTEM_FOLDER",
                        "googleAnalytics", "UA-9877660-3",
                        "tagStorage", "48190c8c-42c4-46af-8d1a-0cd5db894797",
                        "isSystemHost", false,
                        "sortOrder", 0,
                        "modUser", "dotcms.org.1"
                )
             ))) {
                 @Override
                 public boolean isArchived() throws DotStateException, DotDataException, DotSecurityException {
                     return false;
                 }
             }, new Host(new Contentlet(mapAll(
                map(
                        "hostName", "system.dotcms.com",
                        "googleMap", "AIzaSyDXvD7JA5Q8S5VgfviI8nDinAq9x5Utru0",
                        "modDate", Integer.parseInt("125466"),
                        "aliases", "",
                        "keywords", "CMS, System Web Content Management, Open Source, Java, J2EE",
                        "description", "dotCMS starter site was designed to demonstrate what you can do with dotCMS.",
                        "type", "host",
                        "title", "system.dotcms.com",
                        "inode", "54ac9a4e-3d63-4b9a-882f-27c7ba29618f",
                        "hostname", "system.dotcms.com"),
                map(
                        "__DOTNAME__", "system.dotcms.com",
                        "addThis", "ra-4e02119211875e7b",
                        "disabledWYSIWYG", new Object[]{},
                        "host", "SYSTEM_HOST",
                        "lastReview", 14503,
                        "stInode", "855a2d72-f2f3-4169-8b04-ac5157c4380d",
                        "owner", "dotcms.org.1",
                        "identifier", "48190c8c-42c4-46af-8d1a-0cd5db894798",
                        "runDashboard", false,
                        "languageId", 1
                ),
                map(
                        "isDefault", true,
                        "folder", "SYSTEM_FOLDER",
                        "googleAnalytics", "UA-9877660-3",
                        "tagStorage", "48190c8c-42c4-46af-8d1a-0cd5db894799",
                        "isSystemHost", true,
                        "sortOrder", 0,
                        "modUser", "dotcms.org.1"
                )))) {
                 @Override
                 public boolean isArchived() throws DotStateException, DotDataException, DotSecurityException {
                     return false;
                 }
             }
        );
    }

    /**
     * Returns a list of 3 mocked Sites for testing purposes.
     * 
     * @return
     */
    private List<Host> getThreeSites() {
    	return list(new Host(new Contentlet(mapAll(
                map(
                        "hostName", "demo.dotcms.com",
                        "googleMap", "AIzaSyDXvD7JA5Q8S5VgfviI8nDinAq9x5Utmu0",
                        "modDate", Integer.parseInt("125466"),
                        "aliases", "",
                        "keywords", "CMS, Web Content Management, Open Source, Java, J2EE",
                        "description", "dotCMS starter site was designed to demonstrate what you can do with dotCMS.",
                        "type", "host",
                        "title", "demo.dotcms.com",
                        "inode", "54ac9a4e-3d63-4b9a-882f-27c7ba29618f",
                        "hostname", "demo.dotcms.com"),
                map(
                        "__DOTNAME__", "demo.dotcms.com",
                        "addThis", "ra-4e02119211875e7b",
                        "disabledWYSIWYG", new Object[]{},
                        "host", "SYSTEM_HOST",
                        "lastReview", 14503,
                        "stInode", "855a2d72-f2f3-4169-8b04-ac5157c4380c",
                        "owner", "dotcms.org.1",
                        "identifier", "48190c8c-42c4-46af-8d1a-0cd5db894796",
                        "runDashboard", false,
                        "languageId", 1

                ),
                map(
                        "isDefault", true,
                        "folder", "SYSTEM_FOLDER",
                        "googleAnalytics", "UA-9877660-3",
                        "tagStorage", "48190c8c-42c4-46af-8d1a-0cd5db894797",
                        "isSystemHost", false,
                        "sortOrder", 0,
                        "modUser", "dotcms.org.1"
                )
                                      ))) {
                                          @Override
                                          public boolean isArchived() throws DotStateException, DotDataException, DotSecurityException {
                                              return false;
                                          }
                                      }, new Host(new Contentlet(mapAll(
                map(
                        "hostName", "system.dotcms.com",
                        "googleMap", "AIzaSyDXvD7JA5Q8S5VgfviI8nDinAq9x5Utru0",
                        "modDate", Integer.parseInt("125466"),
                        "aliases", "",
                        "keywords", "CMS, System Web Content Management, Open Source, Java, J2EE",
                        "description", "dotCMS starter site was designed to demonstrate what you can do with dotCMS.",
                        "type", "host",
                        "title", "system.dotcms.com",
                        "inode", "54ac9a4e-3d63-4b9a-882f-27c7ba29618f",
                        "hostname", "system.dotcms.com"),
                map(
                        "__DOTNAME__", "system.dotcms.com",
                        "addThis", "ra-4e02119211875e7b",
                        "disabledWYSIWYG", new Object[]{},
                        "host", "SYSTEM_HOST",
                        "lastReview", 14503,
                        "stInode", "855a2d72-f2f3-4169-8b04-ac5157c4380d",
                        "owner", "dotcms.org.1",
                        "identifier", "48190c8c-42c4-46af-8d1a-0cd5db894797",
                        "runDashboard", false,
                        "languageId", 1
                ),
                map(
                        "isDefault", true,
                        "folder", "SYSTEM_FOLDER",
                        "googleAnalytics", "UA-9877660-3",
                        "tagStorage", "48190c8c-42c4-46af-8d1a-0cd5db894799",
                        "isSystemHost", true,
                        "sortOrder", 0,
                        "modUser", "dotcms.org.1"
                )))) {
                                          @Override
                                          public boolean isArchived() throws DotStateException, DotDataException, DotSecurityException {
                                              return false;
                                          }
                                      }
                , new Host(new Contentlet(mapAll(
                        map(
                                "hostName", "demo.awesome.dotcms.com",
                                "googleMap", "AIzaSyDXvD7JA5Q8S5VgfviI8nDinAq9x5Utru0",
                                "modDate", Integer.parseInt("125466"),
                                "aliases", "",
                                "keywords", "CMS, System Web Content Management, Open Source, Java, J2EE",
                                "description", "dotCMS starter site was designed to demonstrate what you can do with dotCMS.",
                                "type", "host",
                                "title", "system.dotcms.com",
                                "inode", "54ac9a4e-3d63-4b9a-882f-27c7dba29618f",
                                "hostname", "system.dotcms.com"),
                        map(
                                "__DOTNAME__", "demo.awesome.dotcms.com",
                                "addThis", "ra-4e02119211875e7b",
                                "disabledWYSIWYG", new Object[]{},
                                "host", "SYSTEM_HOST",
                                "lastReview", 14503,
                                "stInode", "855a2d72-f2f3-4169-8b04-ac5157c4380d",
                                "owner", "dotcms.org.1",
                                "identifier", "48190c8c-42c4-46af-8d1a-0cd5db894798",
                                "runDashboard", false,
                                "languageId", 1
                        ),
                        map(
                                "isDefault", true,
                                "folder", "SYSTEM_FOLDER",
                                "googleAnalytics", "UA-9877660-3",
                                "tagStorage", "48190c8c-42c4-46af-8d1a-0cd5db894799",
                                "isSystemHost", false,
                                "sortOrder", 0,
                                "modUser", "dotcms.org.1"
                        )))) {
                    @Override
                    public boolean isArchived() throws DotStateException, DotDataException, DotSecurityException {
                        return false;
                    }
                }
        );
    }

}
