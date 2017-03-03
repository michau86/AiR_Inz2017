    @Override
    public void run() {
        LOGGER.debug("Communication thread started");
        while (true) {
            if (getCommunicationMode() == CommunicationMode.AUTO) {
                synchronized (atCommandPort.responseSync) {
                    sendToPort(commandObject);
                }
            } else if (getCommunicationMode() == CommunicationMode.MANUAL) {
                synchronized (atCommandPort.responseSync) {
                    setCommunicationMode(CommunicationMode.WAITING);
                    sendToPort(commandObject);
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(ElmMessageService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }