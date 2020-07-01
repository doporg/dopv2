#!/bin/bash
peer channel create -o orderer0:7050 -c <channelName> -f ./scripts/channel-artifacts/channel.tx --tls true --cafile $ORDERER_CA
peer channel join -b mychannel.block
peer channel list