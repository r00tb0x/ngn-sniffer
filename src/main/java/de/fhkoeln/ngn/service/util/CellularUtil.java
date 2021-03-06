package de.fhkoeln.ngn.service.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;

import java.util.List;

import de.fhkoeln.ngn.service.CellularService;
import de.fhkoeln.ngn.service.event.CellularResultEvent;


public class CellularUtil {


    public static String getLTEInfo(CellularResultEvent e) {
        StringBuilder sb = new StringBuilder();
        if (e.getLTECells() != null) {
            for (CellInfo cellInfo : e.getLTECells()) {
                sb.append(cellInfo.describeContents()).append("\n");
            }
        } else {
            sb.append("No LTE cells could be found!");
        }

        return sb.toString();
    }

    public static String getNeighboringCellInfo(CellularResultEvent e) {
        StringBuilder sb = new StringBuilder();
        List<NeighboringCellInfo> nci = e.getTelephonyManager().getNeighboringCellInfo();

        for (NeighboringCellInfo neighboringCellInfo : nci) {
            sb.append("Neighboring Cell Info: ").append(neighboringCellInfo.describeContents()).append("\n")
                    .append("CID: ").append(neighboringCellInfo.getCid()).append("\n")
                    .append("LAC: ").append(neighboringCellInfo.getLac()).append("\n")
                    .append("Network Type: ").append(neighboringCellInfo.getNetworkType()).append("\n")
                    .append("PSC: ").append(neighboringCellInfo.getPsc()).append("\n")
                    .append("RSSI: ").append(neighboringCellInfo.getRssi()).append("\n\n");
        }
        return sb.toString();
    }

    @TargetApi(19)
    public static String getAllInfo(CellularResultEvent e) {
        StringBuilder sb = new StringBuilder();
        TelephonyManager tm = e.getTelephonyManager();

        sb.append("Call State: ");
        switch (tm.getCallState()) {
            case TelephonyManager.CALL_STATE_IDLE:
                sb.append("idle");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                sb.append("off-hook");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                sb.append("ringing");
                break;
            default:
                sb.append("n/a");
        }
        sb.append("\n")

                .append("Data Activity: ");
        switch (tm.getDataActivity()) {
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                sb.append("Dormant");
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                sb.append("In");
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                sb.append("InOut");
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                sb.append("Out");
                break;
            case TelephonyManager.DATA_ACTIVITY_NONE:
                sb.append("None");
                break;
        }
        sb.append("\n")

                .append("Data State: ");
        switch (tm.getDataState()) {
            case TelephonyManager.DATA_DISCONNECTED:
                sb.append("Disconneted");
                break;
            case TelephonyManager.DATA_CONNECTED:
                sb.append("Connected");
                break;
            case TelephonyManager.DATA_CONNECTING:
                sb.append("Connecting");
                break;
            case TelephonyManager.DATA_SUSPENDED:
                sb.append("Suspended");
                break;
        }
        sb.append("\n")
                .append("Device ID (IMEI / MEID): ").append(tm.getDeviceId()).append("\n")
                .append("Device Software Version: ").append(tm.getDeviceSoftwareVersion()).append("\n")
                .append("Group ID Level 1: ").append(tm.getGroupIdLevel1()).append("\n")
                .append("Line 1 Number: ").append(tm.getLine1Number()).append("\n")
                .append("Network Country ISO: ").append(tm.getNetworkCountryIso()).append("\n")
                .append("Network Operator: ").append(tm.getNetworkOperator()).append("\n")
                .append("Network Operator Name: ").append(tm.getNetworkOperatorName()).append("\n")
                .append("Network Type: ");

        sb.append(getNetworkType(tm));

        sb.append("\n")
                .append("Phone Type: ");

        switch (tm.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_CDMA:
                sb.append("CDMA");
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                sb.append("GSM");
                break;
            case TelephonyManager.PHONE_TYPE_NONE:
                sb.append("No phone radio");
                break;
            case TelephonyManager.PHONE_TYPE_SIP:
                sb.append("SIP");
                break;
        }
        sb.append("\n")
                .append("Sim Country ISO: ").append(tm.getSimCountryIso()).append("\n")
                .append("Sim Operator: ").append(tm.getSimOperator()).append("\n")
                .append("Sim Operator Name: ").append(tm.getSimOperatorName()).append("\n")
                .append("Sim Serial Number: ").append(tm.getSimSerialNumber()).append("\n")
                .append("Sim State: ");

        switch (tm.getSimState()) {
            case TelephonyManager.SIM_STATE_ABSENT:
                sb.append("No SIM card");
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                sb.append("SIM card unknown");
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                sb.append("SIM card locked. PIN required");
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                sb.append("SIM card locked. PUNK required");
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                sb.append("SIM card locked. Network PIN required");
                break;
            case TelephonyManager.SIM_STATE_READY:
                sb.append("SIM card ready");
                break;
        }
        sb.append("\n")
                .append("Subscriber ID: ").append(tm.getSubscriberId()).append("\n")
                .append("Voice Mail Alpha Tag: ").append(tm.getVoiceMailAlphaTag()).append("\n")
                .append("Voice Mail number: ").append(tm.getVoiceMailNumber()).append("\n")
                //.append("Carrier privileges: ").append(tm.hasCarrierPrivileges()).append("\n")
                .append("Icc card: ").append(tm.hasIccCard()).append("\n")
                .append("Roaming: ").append(tm.isNetworkRoaming()).append("\n")
                //.append("SMS capable: ").append(tm.isSmsCapable()).append("\n")
                .append("Voice capable: ").append(tm.isVoiceCapable()).append("\n");

        if (Build.VERSION.SDK_INT >= 19) {
            sb.append("MMS UA Prof Url: ").append(tm.getMmsUAProfUrl()).append("\n")
                    .append("MMS User Agent: ").append(tm.getMmsUserAgent()).append("\n");
        }

        sb.append("Cellular signal strength: ").append(CellularService.getTransformedSignalStrength()).append(" dBm\n");

        return sb.toString();
    }

    public static String getNetworkType(TelephonyManager tm) {
        switch (tm.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT ";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO-0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO-A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO-B ";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            default:
                return "unknown";
        }
    }

}
