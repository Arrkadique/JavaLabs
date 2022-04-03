package com.financialsystemmanagement.interfaces;

import java.io.IOException;
import java.util.List;

public interface Personal {
    void showTransfers(List<String> lines, Client client) throws IOException;
    void cancelAction(int bankId, Client client) throws IOException;
    void cancelAllActions(int bankId, Client client) throws IOException;
}
