require linux-stm.inc

# INC_PR is defined in the .inc file if something has change here just increase the number after the dot
PR = "${INC_PR}.4"

PV = "${LINUX_VERSION}-stm24-0212"
SRCREV = "03da591ee397c96e43c84314c582bfe504172a0a"

DEPENDS += " \
           stlinux24-sh4-stx7105-fdma-firmware \
"

SRC_URI_append = "\
             file://linux-sh4-linuxdvb_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-sound_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-time_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-init_mm_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-copro_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-strcpy_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-ext23_as_ext4_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://bpa2_procfs_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-ftdi_sio.c_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-lzma-fix_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-tune_stm24.patch;patch=1 \
             file://linux-sh4-mmap_stm24.patch;patch=1 \
             file://linux-sh4-stmmac_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-lmb_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-spark7162_setup_stm24_${STM_PATCH_STR}.patch;patch=1 \
             file://linux-sh4-cpuinfo.patch;patch=1 \
"
