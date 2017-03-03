    private void executeResponse(List<ResponseObject> responseObject, CommandObject commandObject) {
        for (int i = 0; i < responseObject.size(); i++) {
            if (responseObject.get(i).isResponseOk()) {
                LOGGER.debug("Received response " + responseObject.get(i).getResponse());
                for (String response : responseObject.get(i).getResponse()) {
                    if (checkResponse(response, commandObject.getCmd().get(i).getExpectedResponseRegex())) {
                        if (getCommunicationMode() != CommunicationMode.AUTO) {
                            refreshTextArea(commandObject.getCmd().get(i).getCmd(), response);
                        } else {
                            refreshLabels(commandObject, responseObject);
                        }
                    } 
                }
            }
        }
    }
    
    private boolean checkResponse(String response, String expectedResponse) {
        Pattern expectedResponsePattern = Pattern.compile(expectedResponse);
        Matcher m = expectedResponsePattern.matcher(response);
        return m.matches();
    }