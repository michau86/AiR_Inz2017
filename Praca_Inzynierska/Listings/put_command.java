    public List<byte[]> putAtCommand(String cmd) throws IOException, 
            InterruptedException {
        responseData = new ArrayList<>();
        receivedResponses = 0;
        out.write(cmd.getBytes());
        while (receivedResponses != commandObject.getExpectedResponseLines()) {
            responseSync.wait();
        }
        return responseData;
    }