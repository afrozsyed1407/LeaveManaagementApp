package view.bean;

import java.io.IOException;
import java.io.Serializable;

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.share.ADFContext;

public class MainPageController implements PagePhaseListener,Serializable {
    private static final long serialVersionUID = 1L;


    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
        
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {

        // Connection connection = getConnection();
         try {
             if (true) {
                 // check the OAM HEADER IS GETTING OR NOT
                 System.out.println("inside before Phasse listner");
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                 HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                System.out.println(request.getHeader("OAM_REMOTE_USER"));
        //         String userId=Objects.toString(request.getHeader("OAM_REMOTE_USER"),"ranjan.pujari");
                 
                Map session = ADFContext.getCurrent().getSessionScope();
                      System.out.println(session.get("employeeId"));
                      if(session.get("employeeId")==null){
                          redirectToErrorPage();
                      }
                 
               /*    if (userId == null || userId.isEmpty()) {
                     _logger.info("Found OAM USER as null or empty we are redirecting to error page --> " +
                                  request.getHeader("OAM_REMOTE_USER"));
                     sessionInvalidate();
                     System.out.println("Found OAM USER as null or empty we are redirecting to error page --> " +request.getHeader("OAM_REMOTE_USER"));
                     redirectToErrorPage();
        }
                 else {
                     // set session info.
                     _logger.info("Before Call session info method,Check session values is null or not"+ADFUtils.evaluateEL("#{sessionScope.dealerMapCd}"));
                     _logger.info("When we have changed the noc user:"+ADFUtils.evaluateEL("#{sessionScope.nocUser}"));
                     if(ADFUtils.evaluateEL("#{sessionScope.dealerMapCd}")==null)
                     {
                     _logger.info("Inside first block");    
                     setSessionInfo(userId);
                     }
                     else if(ADFUtils.evaluateEL("#{sessionScope.nocUser}")!=null)
                     {
                         _logger.info("Inside Second block"+ADFUtils.evaluateEL("#{sessionScope.nocUser}"));    
                         setSessionInfo(ADFUtils.evaluateEL("#{sessionScope.nocUser}").toString());
                     }
                     else
                     {
                        
        
                     }
                 }
            }
        // else {
        //                _logger.info("Found Data Source is null or failed redirecting to ErrorPage");
        //                System.out.println("Found Data Source is null or failed redirecting to ErrorPage");
        //                redirectToErrorPage();
        //                System.out.println("End of Found Data Source is null or failed redirecting to ErrorPage");
        //
        //            }  */
             }
         } catch (Exception e) {
             
             e.printStackTrace();
         }

         

    }
    
    private void redirectToErrorPage() {
        System.out.println("Start Calling of redirectToErrorPage");
        String requestContextUrl = FacesContext.getCurrentInstance()
                                               .getExternalContext()
                                               .getRequestContextPath();
        String url = requestContextUrl + "/faces/errorView.jspx";
        System.out.println("Error Page Navigation Url Is -->"+url);
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().redirect(url);
        } catch (IOException ioe) {
            System.out.println("redirectToErrorPage :: IOException :: Raised :: "+ ioe);
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("redirectToErrorPage :: Exception :: Raised :: "+ e);
            e.printStackTrace();
        }
        System.out.println("redirectToErrorPage() : Ends");
    }

}
