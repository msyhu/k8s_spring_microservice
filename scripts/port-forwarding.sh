#!/bin/bash

sudo iptables -A POSTROUTING -t nat -j MASQUERADE -o vboxnet0

# 프로메테우스 31049 - 6515
sudo iptables -I INPUT -p tcp -s 0.0.0.0/0 -d 192.168.0.75 --dport 6515 -j ACCEPT
sudo iptables -I FORWARD -m tcp -p tcp -d 192.168.99.100 --dport 31049 -j ACCEPT
sudo iptables -t nat -I PREROUTING -p tcp -d 192.168.0.75 --dport 6515 -j DNAT --to-destination 192.168.99.100:31049

# 그라파나 32548 - 6514
sudo iptables -I INPUT -p tcp -s 0.0.0.0/0 -d 192.168.0.75 --dport 6514 -j ACCEPT
sudo iptables -I FORWARD -m tcp -p tcp -d 192.168.99.100 --dport 32548 -j ACCEPT
sudo iptables -t nat -I PREROUTING -p tcp -d 192.168.0.75 --dport 6514 -j DNAT --to-destination 192.168.99.100:32548

# swagger 31088 - 6510
sudo iptables -I INPUT -p tcp -s 0.0.0.0/0 -d 192.168.0.75 --dport 6510 -j ACCEPT
sudo iptables -I FORWARD -m tcp -p tcp -d 192.168.99.100 --dport 31088  -j ACCEPT
sudo iptables -t nat -I PREROUTING -p tcp -d 192.168.0.75 --dport 6510 -j DNAT --to-destination 192.168.99.100:31088