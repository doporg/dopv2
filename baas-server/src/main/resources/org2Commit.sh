#!/bin/bash
peer lifecycle chaincode approveformyorg --channelID mychannel --name marbles --version 1.0 --init-required --package-id <ccid> --sequence 1 -o orderer0:7050 --tls --cafile $ORDERER_CA --signature-policy "AND ('org1MSP.peer','org2MSP.peer')"
