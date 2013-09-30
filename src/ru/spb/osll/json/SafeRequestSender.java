/**
 * 
 */
package ru.spb.osll.json;

import org.json.JSONObject;


/**
 * This class allows to send requests safely especially on mobile devices -
 *  different errno values are supported, several attempts in case of bad network, exception based
 *  results
 * @author Mark Zasalavskiy
 *
 */
public class SafeRequestSender {

    /**
     * @param request 
     * @param response
     * @param attempts - number of request sending attempts 
     * @param possibleErrnos - possible values of errno
     * @throws RequestException
     */
    public static  void safeSendingRequest(JsonBaseRequest request, 
        JsonBaseResponse response, int attempts, int... possibleErrnos) 
        throws RequestException 
    {
        JSONObject JSONResponse = null;
        for (int i = 0; i < attempts && JSONResponse == null; i++) {
            JSONResponse = request.doRequest();
        }
        if (JSONResponse == null){
            throw new RequestException(Errno.EMPTY_SERVER_RESPONER_ERROR);
        }
        response.parseJson(JSONResponse);
        int errno =  response.getErrno();
        
        if (errno == JsonBaseResponse.INVALID_ERRNO){
        	throw new RequestException(Errno.JSON_PARSING_ERROR);
        }
        
        for (int err : possibleErrnos) {
            if (err == errno) return;
        }
        
        throw new RequestException(errno);
    }
}
