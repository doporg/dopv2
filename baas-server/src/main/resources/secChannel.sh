#!/bin/bash
peer channel fetch 0 mychannel.block -c <channelName> -o orderer0:7050 --tls --cafile $ORDERER_CA
peer channel join -b mychannel.block
peer channel list