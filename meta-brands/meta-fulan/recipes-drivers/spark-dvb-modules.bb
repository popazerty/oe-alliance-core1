SUMMARY = "Spark7162 driver modules from TDT"
DESCRIPTION = "Spark7162 driver modules from TDT"
SRCDATE = "20141006"

require spark-dvb-modules.inc

PR = "${INC_PR}.1"

do_install_append() {
    find ${D} -name stmcore-display-sti7106.ko | xargs -r rm # we don't have a 7106 chip
}

