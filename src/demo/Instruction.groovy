package demo

/**
 * Created by Badger on 16/1/7.
 */
class Instruction {

    //接收到的Json字符串
    public static String receiveJsonDemo = """
{
    "Instructions": [
        {
            "content": {
                "batchId": "MoveEvent_20160108_1032",
                "moveId": 1,
                "moveKind": "Load",
                "state": 0,
                "unitId": "CBHU2677230",
                "unitLength": "20'",
                "exFromPosition": {
                    "area": "Y-DLT-44",
                    "bay": "03",
                    "lay": "04",
                    "tie": "01"
                },
                "exToPosition": {
                    "area": "V-LH526002-A",
                    "bay": "1:",
                    "lay": "L1",
                    "tie": "58"
                },
                "acFromPosition": {
                    "area": "Y-DLT-44",
                    "bay": "03",
                    "lay": "04",
                    "tie": "01"
                },
                "acToPosition": {
                    "area": "V-LH526002-A",
                    "bay": "1:",
                    "lay": "L1",
                    "tie": "58"
                },
                "fetchCHE": "32",
                "carryCHE": "32",
                "putCHE": "32",
                "fetchTime": "2016-01-09 00:12:00",
                "carryTime": "2016-01-09 00:12:00",
                "putTime": "2016-01-09 00:12:00"
            }
        },
        {
            "content": {
                "batchId": "MoveEvent_20160108_1032",
                "moveId": 2,
                "moveKind": "Load",
                "state": 0,
                "unitId": "CBHU2686083",
                "unitLength": "20'",
                "exFromPosition": {
                    "area": "Y-DLT-44",
                    "bay": "03",
                    "lay": "04",
                    "tie": "02"
                },
                "exToPosition": {
                    "area": "V-LH526002",
                    "bay": "03",
                    "lay": "00",
                    "tie": "02"
                },
                "acFromPosition": {
                    "area": "Y-DLT-44",
                    "bay": "03",
                    "lay": "04",
                    "tie": "02"
                },
                "acToPosition": {
                    "area": "V-LH526002",
                    "bay": "03",
                    "lay": "00",
                    "tie": "02"
                },
                "fetchCHE": "123",
                "carryCHE": "123",
                "putCHE": "123",
                "fetchTime": "2016-01-09 00:12:00",
                "carryTime": "2016-01-09 00:12:00",
                "putTime": "2016-01-09 00:12:00"
            }
        },
        {
            "content": {
                "batchId": "MoveEvent_20160108_1032",
                "moveId": 3,
                "moveKind": "Load",
                "state": 0,
                "unitId": "TRIU6660703",
                "unitLength": "20'",
                "exFromPosition": {
                    "area": "Y-DLT-44",
                    "bay": "03",
                    "lay": "01",
                    "tie": "01"
                },
                "exToPosition": {
                    "area": "V-LH526002",
                    "bay": "03",
                    "lay": "02",
                    "tie": "02"
                },
                "acFromPosition": {
                    "area": "Y-DLT-44",
                    "bay": "03",
                    "lay": "01",
                    "tie": "01"
                },
                "acToPosition": {
                    "area": "V-LH526002",
                    "bay": "03",
                    "lay": "02",
                    "tie": "02"
                },
                "fetchCHE": "123",
                "carryCHE": "123",
                "putCHE": "123",
                "fetchTime": "2016-01-09 00:12:00",
                "carryTime": "2016-01-09 00:12:00",
                "putTime": "2016-01-09 00:12:00"
            }
        }
    ],
    "dispatchTime": "2016-01-09 00:12:00"
}
"""
}
