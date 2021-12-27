import json
import csv
from os import remove
import re

def remove_non_existing():
    data = []
    with open('법정동코드.txt', 'r', encoding="utf-8") as f:
        for line in f.readlines():
            cols = line.rstrip().split("\t")
            if cols[2] in {"폐지", "폐지여부"}:
                continue
            else:
                data.append(line)
    with open("법정동코드_현행.txt", 'w', encoding="utf8") as f:
        f.writelines(data)

def dump_to_sql():
    with open("법정동코드_현행.txt", "r", encoding="utf8") as f:
        with open("sgg.csv", "w", encoding="cp949", newline="") as sggfile:
            sggcsv = csv.writer(sggfile)
            with open("umd.csv", 'w', encoding="cp949", newline="") as umdfile:
                with open("bjd.csv", "w", encoding="cp949", newline="") as bjdfile:
                    umdcsv = csv.writer(umdfile)
                    bjdcsv = csv.writer(bjdfile)
                    for line in f.readlines():
                        code, address, _ = line.split("\t")
                        sgg, umd = code[:5], code[5:]
                        bjdcsv.writerow([sgg, umd, address])
                        if umd == "00000":
                            sggcsv.writerow([sgg, address])
                        else:
                            try:
                                address = re.findall("(?:.*군|.*구|.*시) (.*)", address)[0]
                            except IndexError:
                                print(address)
                            umdcsv.writerow([umd, address, sgg])

def sido_sgg():
    sido, sgg, umd = dict(), dict(), dict()
    with open("법정동코드_현행.txt", 'r', encoding="utf8") as origin:
        with open("sido.csv", "w", encoding="cp949", newline="") as sidof:
            sidocsv = csv.writer(sidof)
            with open("sgg.csv", "w", encoding="cp949", newline="") as sggf:
                sggcsv = csv.writer(sggf)
                with open("umd.csv", "w", encoding="cp949", newline="") as umdf:
                    umdcsv = csv.writer(umdf)
                    for line in origin.readlines():
                        code, address, _ = line.split("\t")
                        # split code
                        sidoCode, sggCode, umdCode = code[:2], code[2:5], code[5:8]
                        if sidoCode not in sido:
                            sido[sidoCode] = address
                            sgg[sidoCode] = dict()
                        if (int(sggCode) > 0 and not address.startswith("세종특별자치시")) and sggCode not in sgg[sidoCode]:
                            try:
                                sgg[sidoCode][sggCode] = address.split()[1]
                            except:
                                print("Exception", address)
                        if code[:5] not in umd:
                            umd[code[:5]] = dict()
                        if umdCode not in umd[code[:5]] and len(address.split()) > 2 and code[8:] == "00":
                            umd[code[:5]][umdCode] = " ".join(address.split()[2:])

                    sgg['36']['110'] = "세종특별자치시"
                    print(sido, sgg)
                    for code in sido.keys():
                        sidocsv.writerow([code, sido[code]])
                        for subcode in sgg[code].keys():
                            sggcsv.writerow([subcode, sgg[code][subcode], code])
                            for umdcode in umd[code+subcode].keys():
                                umdcsv.writerow([umdcode, umd[code+subcode][umdcode], code, subcode])
                

if __name__ == "__main__":
    #remove_non_existing()
    sido_sgg()
