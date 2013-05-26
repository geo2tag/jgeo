/**
 * 
 */
package ru.spb.osll.json;

import org.json.JSONObject;


/**
 * This class allows to send requests safely especialy on mobile devices -
 *  different errno values are supported, several attempts in case of bad network, exception based
 *  results
 * @author Mark Zasalavskiy
 *
 */
public class SafeRequestSender {

    /**
     * @param request 
     * @param response
     * @param emptyResponseMsg - message to write in case of empty response
     * @param wrongResponseMsg - message to write in case of bad errno
     * @param attempts - number of request sending attempts 
     * @param possibleErrnos - possible values of errno
     * @throws RequestException
     */
    public static  void safeSendingRequest(JsonBaseRequest request, 
        JsonBaseResponse response, String emptyResponseMsg, String wrongResponseMsg,
        int attempts, int[] possibleErrnos) 
        throws RequestException 
    {
        JSONObject JSONResponse = null;
        for (int i = 0; i < attempts && JSONResponse == null; i++) {
            JSONResponse = request.doRequest();
        }
        if (JSONResponse == null){
            throw new RequestException(emptyResponseMsg);
        }
        response.parseJson(JSONResponse);
        int errno =  response.getErrno();
        for (int err : possibleErrnos) {
            if (err == errno) return;
        }
        
        throw new RequestException(wrongResponseMsg+errno);
    }
}
