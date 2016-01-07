package demo

/**
 * Created by Badger on 16/1/7.
 */
class Instruction {

    //接收到的Json字符串
    static String receiveJsonDemo = """
{
    "Instructions": [
        {
            "content": {
                "batchId": "batch01",
                "moveId": "1",
                "moveKind": "2",
                "state": "0",
                "unitId": "unit001",
                "aFromPosition": {
                    "area": "yard-01",
                    "bay": "07",
                    "lay": "02",
                    "tie": "02"
                },
                "aToPosition": {
                    "area": "vessel-003",
                    "bay": "05",
                    "lay": "01",
                    "tie": "03"
                },
                "fetchCHE": "unit002",
                "carryCHE": "unit002",
                "putCHE": "unit002",

                "dispatchTime": "2016-01-07 21:51:47",
                "fetchTime": "2016-01-07 21:51:47",
                "carryTime": "2016-01-07 21:51:47",
                "putTime": "2016-01-07 21:51:47"
            }
        },
        {
            "content": {
                "batchId": "batch02",
                "moveId": "1",
                "moveKind": "2",
                "state": "0",
                "unitId": "unit002",
                "aFromPosition": {
                    "area": "yard-01",
                    "bay": "07",
                    "lay": "02",
                    "tie": "02"
                },
                "aToPosition": {
                    "area": "vessel-003",
                    "bay": "05",
                    "lay": "01",
                    "tie": "03"
                },
                "fetchCHE": "unit002",
                "carryCHE": "unit002",
                "putCHE": "unit002",

                "dispatchTime": "2016-01-07 21:51:47",
                "fetchTime": "2016-01-07 21:51:47",
                "carryTime": "2016-01-07 21:51:47",
                "putTime": "2016-01-07 21:51:47"
            }
        }
    ]
}
"""
}
