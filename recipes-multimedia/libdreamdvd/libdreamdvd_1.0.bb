--- libdreamdvd-6aa22dd3f530ca4be49946e07e4a0bfe60427bdf/main.c.org	2013-05-24 20:29:13.000000000 +0200
+++ libdreamdvd-6aa22dd3f530ca4be49946e07e4a0bfe60427bdf/main.c	2013-06-22 19:12:45.000000000 +0200
@@ -129,7 +129,11 @@
 	// defaults
 	ddvd_set_ac3thru(pconfig, 0);
 	ddvd_set_language(pconfig, "en");
+#if defined(__sh__)
+	ddvd_set_dvd_path(pconfig, "/dev/sr0");
+#else
 	ddvd_set_dvd_path(pconfig, "/dev/cdroms/cdrom0");
+#endif
 	ddvd_set_video(pconfig, DDVD_4_3, DDVD_LETTERBOX, DDVD_PAL);
 	ddvd_set_lfb(pconfig, NULL, 720, 576, 1, 720);
 	struct ddvd_resume resume_info;
@@ -612,8 +616,11 @@
 	unsigned char *p_lfb = playerconfig->lfb;
 	enum ddvd_result res = DDVD_OK;
 	int msg;
+#if defined(__sh__)
+#else
 	// try to load liba52.so.0 for softdecoding
 	int have_liba52 = ddvd_load_liba52();
+#endif
 
 	// decide which resize routine we should use
 	// on 4bpp mode we use bicubic resize for sd skins because we get much better results with subtitles and the speed is ok
@@ -730,7 +737,11 @@
 		perror("LIBDVD: AUDIO_PLAY");
 
 #elif CONFIG_API_VERSION == 3
+#if defined(__sh__)
+	ddvd_output_fd = ddvd_fdvideo = open("/dev/dvb/adapter0/video0", O_RDWR | O_NONBLOCK/*else get_video_event would block*/);
+#else
 	ddvd_output_fd = ddvd_fdvideo = open("/dev/dvb/adapter0/video0", O_RDWR);
+#endif
 	if (ddvd_fdvideo == -1) {
 		perror("LIBDVD: /dev/dvb/adapter0/video0");
 		res = DDVD_BUSY;
@@ -772,8 +783,12 @@
 		safe_write(ddvd_output_fd, ddvd_startup_logo, sizeof(ddvd_startup_logo));
 # else
 	unsigned char pes_header[] = { 0x00, 0x00, 0x01, 0xE0, 0x00, 0x00, 0x80, 0x00, 0x00 };
+	unsigned char stuffing[8192];
+	memset(stuffing, 0, 8192);
+
 	safe_write(ddvd_output_fd, pes_header, sizeof(pes_header));
 	safe_write(ddvd_output_fd, ddvd_startup_logo, sizeof(ddvd_startup_logo));
+	safe_write(ddvd_output_fd, stuffing, 8192);
 # endif
 #endif
 
@@ -797,10 +812,13 @@
 	ddvd_mpa_init(48000, 192000);	//init MPA Encoder with 48kHz and 192k Bitrate
 
 	int ac3thru = 1;
+#if defined(__sh__)
+#else
 	if (have_liba52) {
 		state = a52_init(0);	//init AC3 Decoder
 		ac3thru = playerconfig->ac3thru;
 	}
+#endif
 
 	char osdtext[512];
 	osdtext[0] = 0;
@@ -881,7 +899,11 @@
 	}
 
 	/* set read ahead cache usage to no */
+#if defined(__sh__)
+	if (dvdnav_set_readahead_flag(dvdnav, READAHEAD) != DVDNAV_STATUS_OK) {
+#else
 	if (dvdnav_set_readahead_flag(dvdnav, 0) != DVDNAV_STATUS_OK) {
+#endif
 		Debug(1, "Error on dvdnav_set_readahead_flag: %s\n", dvdnav_err_to_string(dvdnav));
 		res = DDVD_FAIL_PREFS;
 		goto err_dvdnav;
@@ -1008,6 +1030,11 @@
 				ddvd_spu_play = ddvd_spu_ind; // skip remaining subtitles
 			}
 
+#if defined(__sh__)
+			if(READAHEAD == 1)
+				result = dvdnav_get_next_cache_block(dvdnav, &buf, &event, &len);
+			else
+#endif
 			result = dvdnav_get_next_block(dvdnav, buf, &event, &len);
 			if (result == DVDNAV_STATUS_ERR) {
 				Debug(1, "Error getting next block: %s\n", dvdnav_err_to_string(dvdnav));
@@ -1097,8 +1124,14 @@
 						safe_write(ddvd_output_fd, last_iframe, ddvd_last_iframe_len);
 #else
 					safe_write(ddvd_output_fd, last_iframe, ddvd_last_iframe_len);
+#if defined(__sh__)
+					unsigned char stuffing[8192];
+					memset(stuffing, 0, 8192);
+					safe_write(ddvd_output_fd, stuffing, 8192);
+#else
 					safe_write(ddvd_output_fd, last_iframe, ddvd_last_iframe_len); // send twice to avoid no-display...
 #endif
+#endif
 					//Debug(1, "Show iframe with size: %d\n",ddvd_last_iframe_len);
 					ddvd_last_iframe_len = 0;
 				}
@@ -1262,10 +1295,22 @@
					else if ((buf[14 + 3]) == 0xC0 + audio_id) {	// mpeg audio
						if (audio_type != DDVD_MPEG) {
							//Debug(1, "Switch to MPEG Audio\n");
+#ifdef __sh__
+							//stop audio bevor change encoding
+							if (ioctl(ddvd_fdaudio, AUDIO_STOP) < 0)
+								perror("LIBDVD: AUDIO_STOP");
+							if (ioctl(ddvd_fdaudio, AUDIO_CLEAR_BUFFER) < 0)
+								perror("LIBDVD: AUDIO_CLEAR_BUFFER");
+#endif
							if (ioctl(ddvd_fdaudio, AUDIO_SET_AV_SYNC, 1) < 0)
								Perror("AUDIO_SET_AV_SYNC");
							if (ioctl(ddvd_fdaudio, AUDIO_SET_BYPASS_MODE, 1) < 0)
								Perror("AUDIO_SET_BYPASS_MODE");
+#ifdef __sh__
+							//start audio after encoding set
+							if (ioctl(ddvd_fdaudio, AUDIO_PLAY) < 0)
+								perror("LIBDVD: AUDIO_PLAY");
+#endif
							audio_type = DDVD_MPEG;
						}

@@ -1292,10 +1337,22 @@
 
 						if (audio_type != DDVD_LPCM) {
 							//Debug(1, "Switch to LPCM Audio\n");
+#ifdef __sh__
+							//stop audio bevor change encoding
+							if (ioctl(ddvd_fdaudio, AUDIO_STOP) < 0)
+								perror("LIBDVD: AUDIO_STOP");
+							if (ioctl(ddvd_fdaudio, AUDIO_CLEAR_BUFFER) < 0)
+								perror("LIBDVD: AUDIO_CLEAR_BUFFER");
+#endif
							if (ioctl(ddvd_fdaudio, AUDIO_SET_AV_SYNC, 1) < 0)
								Perror("AUDIO_SET_AV_SYNC");
							if (ioctl(ddvd_fdaudio, AUDIO_SET_BYPASS_MODE, lpcm_mode) < 0)
								Perror("AUDIO_SET_BYPASS_MODE");
+#ifdef __sh__
+							//start audio after encoding set
+							if (ioctl(ddvd_fdaudio, AUDIO_PLAY) < 0)
+								perror("LIBDVD: AUDIO_PLAY");
+#endif
 							audio_type = DDVD_LPCM;
 							ddvd_lpcm_count = 0;
 						}
@@ -1360,6 +1417,13 @@
 					else if ((buf[14 + 3]) == 0xBD && (buf[14 + buf[14 + 8] + 9]) == 0x88 + audio_id) {	// dts audio
 						if (audio_type != DDVD_DTS) {
 							//Debug(1, "Switch to DTS Audio (thru)\n");
+#ifdef __sh__
+							//stop audio bevor change encoding
+							if (ioctl(ddvd_fdaudio, AUDIO_STOP) < 0)
+								perror("LIBDVD: AUDIO_STOP");
+							if (ioctl(ddvd_fdaudio, AUDIO_CLEAR_BUFFER) < 0)
+								perror("LIBDVD: AUDIO_CLEAR_BUFFER");
+#endif
 							if (ioctl(ddvd_fdaudio, AUDIO_SET_AV_SYNC, 1) < 0)
 								perror("LIBDVD: AUDIO_SET_AV_SYNC");
 #ifdef CONVERT_TO_DVB_COMPLIANT_DTS
@@ -1368,6 +1432,11 @@
 							if (ioctl(ddvd_fdaudio, AUDIO_SET_BYPASS_MODE, 5) < 0)	// DTS VOB
 #endif
								Perror("AUDIO_SET_BYPASS_MODE");
+#ifdef __sh__
+							//start audio after encoding set
+							if (ioctl(ddvd_fdaudio, AUDIO_PLAY) < 0)
+								perror("LIBDVD: AUDIO_PLAY");
+#endif
 							audio_type = DDVD_DTS;
 						}
 
@@ -1397,7 +1466,11 @@
 						if (audio_type != DDVD_AC3) {
 							//Debug(1, "Switch to AC3 Audio\n");
 							int bypassmode;
+#if defined(__sh__)
+							if (ac3thru)
+#else
 							if (ac3thru || !have_liba52) // !have_liba52 and !ac3thru should never happen, but who knows ;)
+#endif
 #ifdef CONVERT_TO_DVB_COMPLIANT_AC3
 								bypassmode = 0;
 #else
@@ -1405,10 +1478,25 @@
 #endif
 							else
 								bypassmode = 1;
+#ifdef __sh__
+							//stop audio bevor change encoding
+							if (ioctl(ddvd_fdaudio, AUDIO_STOP) < 0)
+								perror("LIBDVD: AUDIO_STOP");
+							if (ioctl(ddvd_fdaudio, AUDIO_CLEAR_BUFFER) < 0)
+								perror("LIBDVD: AUDIO_CLEAR_BUFFER");
+#endif
+
							if (ioctl(ddvd_fdaudio, AUDIO_SET_AV_SYNC, 1) < 0)
								Perror("AUDIO_SET_AV_SYNC");
							if (ioctl(ddvd_fdaudio, AUDIO_SET_BYPASS_MODE, bypassmode) < 0)
									Perror("AUDIO_SET_BYPASS_MODE");
+
+#ifdef __sh__
+							//start audio after encoding set
+							if (ioctl(ddvd_fdaudio, AUDIO_PLAY) < 0)
+								perror("LIBDVD: AUDIO_PLAY");
+#endif
+
 							audio_type = DDVD_AC3;
 						}
 
@@ -1422,7 +1510,14 @@
 							//Debug(1, "APTS=%X\n",(int)apts);
 						}
 
+#if defined(__sh__)
+						if (ac3thru) {
+#else
 						if (ac3thru || !have_liba52) {	// !have_liba52 and !ac3thru should never happen, but who knows ;)
+#endif
+#if defined(__sh__)
+							safe_write(ddvd_ac3_fd, buf + 14, buf[19] + (buf[18] << 8) + 6);
+#else
 #ifdef CONVERT_TO_DVB_COMPLIANT_AC3
 							unsigned short pes_len = (buf[14 + 4] << 8 | buf[14 + 5]);
 							pes_len -= 4;	// strip first 4 bytes of pes payload
@@ -1434,6 +1529,7 @@
 #else
 							safe_write(ddvd_ac3_fd, buf + 14, buf[19] + (buf[18] << 8) + 6);
 #endif
+#endif /* __sh__ */
 							//fwrite(buf + buf[22] + 27, 1, ((buf[18] << 8) | buf[19]) - buf[22] - 7, fac3); //debugwrite
 						}
 						else {
@@ -1815,8 +1911,9 @@
 				/* A NAV packet provides PTS discontinuity information, angle linking information and
 				 * button definitions for DVD menus. We have to handle some stilframes here */
 				// Debug(3, "DVDNAV_NAV_PACKJET vpts=%llu pts=%llu highlight=%d\n", vpts, pts, have_highlight);
-				if ((ddvd_still_frame & NAV_STILL) && ddvd_iframesend == 0 && ddvd_last_iframe_len)
+				if ((ddvd_still_frame & NAV_STILL) && ddvd_iframesend == 0 && ddvd_last_iframe_len) {
 					ddvd_iframesend = 1;
+				}
 
 				dsi = dvdnav_get_current_nav_dsi(dvdnav);
 				if (dsi->vobu_sri.next_video == 0xbfffffff)
@@ -2709,10 +2806,13 @@
 	close(ddvd_output_fd);
 err_open_output_fd:
 
+#if defined(__sh__)
+#else
 	if (have_liba52) {
 		a52_free(state);
 		ddvd_close_liba52();
 	}
+#endif
 
 	//Clear Screen
 	blit_area.x_start = blit_area.y_start = 0;
--- libdreamdvd-6aa22dd3f530ca4be49946e07e4a0bfe60427bdf/main.h.org	2013-05-24 20:29:13.000000000 +0200
+++ libdreamdvd-6aa22dd3f530ca4be49946e07e4a0bfe60427bdf/main.h	2013-06-22 18:50:22.601056661 +0200
@@ -32,6 +32,11 @@
 
 #include "libdreamdvd_config.h"
 
+#if defined(__sh__)
+#define READAHEAD 1
+#else
+#define READAHEAD 0
+#endif
 // set to 1 if a start screen should be displayed
 #define SHOW_START_SCREEN 1
 
