DESCRIPTION = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = " GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "gstreamer gst-plugins-base libdca tdt-dvb-modules-${MACHINE}"

SRC_URI = "git://github.com/sklnet/tdt-tools.git;protocol=git \
           file://COPYING \
"

S = "${WORKDIR}/git/gst-plugins-dvbmediasink"

inherit gitpkgv

PV = "0.10.1+git${SRCPV}"
PKGV = "0.10.1+git${GITPKGV}"
PR = "r1"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG}"
