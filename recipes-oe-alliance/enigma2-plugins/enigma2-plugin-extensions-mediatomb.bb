SUMMARY = "UPnP media server"
RDEPENDS_${PN} = "mediatomb expat libav sqlite3 libexif mozjs zlib file id3lib"
#RDEPENDS_${PN} = "mediatomb expat libav sqlite3 libexif zlib file id3lib"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PV = "0.12.1"
PR = "r0"

S = "${WORKDIR}"

ALLOW_EMPTY_${PN} = "1"
