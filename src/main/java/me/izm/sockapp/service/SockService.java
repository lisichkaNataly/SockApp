package me.izm.sockapp.service;

import me.izm.sockapp.dto.SockRequest;
import me.izm.sockapp.exception.InSufficientSockQuantity;
import me.izm.sockapp.exception.InvalidSockRequestException;
import me.izm.sockapp.model.Colour;
import me.izm.sockapp.model.Size;
import me.izm.sockapp.model.Sock;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class SockService {
    private final Map<Sock, Integer> socks = new HashMap<>();

    public void addSock(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        if (socks.containsKey(sock)) {
            socks.put(sock, socks.get(sock) + sockRequest.getQuantity());
        } else {
            socks.put(sock, sockRequest.getQuantity());
        }
    }

    public void issueSock(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }

    public void removeDefectiveSocks(SockRequest sockRequest) {
        decreaseSockQuantity(sockRequest);
    }

    private void decreaseSockQuantity(SockRequest sockRequest) {
        validateRequest(sockRequest);
        Sock sock = mapToSock(sockRequest);
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= sockRequest.getQuantity()) {
            socks.put(sock, sockQuantity - sockRequest.getQuantity());
        } else {
            throw new InSufficientSockQuantity("Носков нет / There is no socks");
        }
    }

    public int getSocksQuantity(Colour colour, Size size, Integer cottonMin, Integer cottonMax) {
        int total = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (colour != null && !entry.getKey().getColour().equals(colour)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPercentage() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPercentage() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return total;
    }

    private void validateRequest(SockRequest sockRequest) {
        if (sockRequest.getColour() == null || sockRequest.getSize() == null) {
            throw new InvalidSockRequestException("Все поля должны быть заполнены/All fields should be filled");
        }
        if (sockRequest.getCottonPercentage() < 0 || sockRequest.getCottonPercentage() > 100) {
            throw new InvalidSockRequestException("Процент хлопка должен быть между 0 и 100 / Cotton Percentage should be between 0 and 100");
        }
        if (sockRequest.getQuantity() <= 0) {
            throw new InvalidSockRequestException("Количество должно быть больше 0 / Quantity should be more than 0");
        }

    }

    private Sock mapToSock(SockRequest sockRequest) {
        Sock sock = new Sock(sockRequest.getColour(), sockRequest.getSize(), sockRequest.getCottonPercentage());
        return sock;
    }


}
