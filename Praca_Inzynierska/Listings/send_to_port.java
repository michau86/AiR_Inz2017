    public List<ResponseObject> sendToPort(CommandObject commandObject) {
        List<ResponseObject> responseObject = new ArrayList<>();
        LOGGER.debug("Sending message " + commandObject.getCmd());
        LOGGER.debug("Expected response lines " + commandObject.getExpectedResponseLines());
        try {
            atCommandPort.setCommandObject(commandObject);
            for (int i = 0; i < commandObject.getCmd().size(); i++) {
                ResponseObject co = new ResponseObject(atCommandPort.putAtCommand(commandObject.getCmd().get(i).getCmd()));
                responseObject.add(co);
            }
        } catch (IOException | InterruptedException ex) {
            LOGGER.error("Cannot send cmd to port : " + ex);
            responseObject.add(new ResponseObject());
        }
        if (responseObject.size() > 0) {
            executeResponse(responseObject, commandObject);
        }
        return responseObject;
    }