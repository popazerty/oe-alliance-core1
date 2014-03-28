require conf/machine/include/tune-sh4.inc

BRAND_OEM = "spark"
MACHINE_BRAND = "Spark"
TARGET_ARCH = "sh4"
STLINUX_SH_UPD_SRPMS = "http://ftp.stlinux.com/pub/stlinux/2.4/updates/SRPMS"
STLINUX_SH_UPD_RPMS = "http://ftp.stlinux.com/pub/stlinux/2.4/updates/RPMS/sh4/"
STM_PATCH_STR ??= "0212"
LINUX_VERSION ??= "2.6.32.61"

GCCVERSION = "4.7%"

PREFERRED_PROVIDER_virtual/kernel = "stlinux24-${MACHINE}"
PREFERRED_VERSION_stlinux24-${MACHINE} = "${LINUX_VERSION}-stm24-${STM_PATCH_STR}"
PREFERRED_VERSION_linux-libc-headers = "2.6.32"
PREFERRED_PROVIDER_showiframe = "tdt-utils-${MACHINE}"
PREFERRED_PROVIDER_directfb = "stm-directfb"

USE_VT = "0"
MACHINE_FEATURES = "kernel26 ext2 alsa usbhost serial"

SERIAL_CONSOLE = "115200 ttyAS0"

KERNEL_IMAGETYPE = "uImage"

# save some extra space, kernel has its own mtd partion 
RDEPENDS_kernel-base = ""

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 129024 -c 638   -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB -O 512"

JFFS2_ERASEBLOCK = "0x20000"
JFFS2_ENDIANNESS = "${@base_conditional('SITEINFO_ENDIANNESS', 'le', '--little-endian', '--big-endian', d)}"
EXTRA_IMAGECMD_jffs2 = "--pad ${JFFS2_ENDIANNESS} --eraseblock=${JFFS2_ERASEBLOCK} --no-cleanmarkers"

INHERIT += "usb_image-spark71xx"
IMAGE_FSTYPES ?= "tar.gz ubi ${SDIMG_ROOTFS_TYPE} jffs2 spark71xx-usbimg"

IMAGE_CLASSES += "usb_image-spark71xx"
INITRAMFS_FSTYPES = "ext2.gz spark-updt"

MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
    stlinux24-sh4-stx7111-fdma-firmware \
    stlinux24-sh4-stmfb-firmware-stx7111 \
    stslave-fw \
    lirc \
    lrzsz \
    mtd-utils \
    mtd-utils-jffs2 \
    mtd-utils-ubifs \
    mtd-utils-misc \
    tdt-utils-${MACHINE} \
    tdt-dvb-modules-${MACHINE} \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    tdt-gst-plugin-dvbmediasink \
    "

#EGLIBCVERSION ?= "2.17"
OLDEST_KERNEL = "2.6.32"

