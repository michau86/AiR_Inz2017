    private class Callback implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent spe) {
            byte[] responseLine = new byte[256];
            synchronized (responseSync) {
                if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    try {
                        int av = in.available();
                        int read = in.read(responseLine, 0, av);
                        responseData.add(responseLine);
                        receivedResponses++;
                        responseSync.notifyAll();
                    } catch (IOException ioe) {
                        LOGGER.error("Cannot read data from port " + ioe);
                    }
                }
            }
        }
    }