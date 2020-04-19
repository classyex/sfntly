package com.github.classyex.sfntlyapi.util;

import cn.hutool.core.util.StrUtil;
import com.github.classyex.sfntlyapi.api.FontExtension;
import com.github.classyex.sfntlyapi.common.exception.AppException;
import com.google.typography.font.sfntly.Font;
import com.google.typography.font.sfntly.FontFactory;
import com.google.typography.font.sfntly.Tag;
import com.google.typography.font.sfntly.data.WritableFontData;
import com.google.typography.font.sfntly.table.core.CMapTable;
import com.google.typography.font.tools.conversion.eot.EOTWriter;
import com.google.typography.font.tools.conversion.woff.WoffWriter;
import com.google.typography.font.tools.sfnttool.GlyphCoverage;
import com.google.typography.font.tools.subsetter.HintStripper;
import com.google.typography.font.tools.subsetter.RenumberingSubsetter;
import com.google.typography.font.tools.subsetter.Subsetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.classyex.sfntlyapi.common.exception.ExceptionEnum.WRONG_REQ;


/**
 * @author yex <br>
 * @version 1.0 <br>
 * @date 2020/3/25 16:21 <br>
 */
public class SfntToolWrapper {

    private String subsetString;
    private boolean strip = false;
    private boolean woff = false;
    private boolean eot = false;
    private boolean mtx = false;
    private int nIters = 1;


    public SfntToolWrapper(String content, FontExtension type) {
        if (StrUtil.isBlank(content)) {
            throw new AppException(WRONG_REQ);
        }
        this.subsetString = content;
        switch (type) {
            case woff:
                this.woff = true;
                break;
            case eot:
                this.eot = true;
                break;
            default:
                break;
        }
    }

    public void subsetFontFile(String fontFile, String outFile) throws IOException {
        subsetFontFile(new File(fontFile), new File(outFile), nIters);
    }

    public void subsetFontFile(File fontFile, File outFile) throws IOException {
        subsetFontFile(fontFile, outFile, nIters);
    }

    public void subsetFontFile(File fontFile, File outputFile, int nIters)
            throws IOException {
        FontFactory fontFactory = FontFactory.getInstance();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fontFile);
            byte[] fontBytes = new byte[(int)fontFile.length()];
            fis.read(fontBytes);
            Font[] fontArray = fontFactory.loadFonts(fontBytes);
            Font font = fontArray[0];
            List<CMapTable.CMapId> cmapIds = new ArrayList<CMapTable.CMapId>();
            cmapIds.add(CMapTable.CMapId.WINDOWS_BMP);
            for (int i = 0; i < nIters; i++) {
                extraSubSet(outputFile, fontFactory, font, cmapIds);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private void extraSubSet(File outputFile, FontFactory fontFactory, Font font, List<CMapTable.CMapId> cmapIds) throws IOException {
        Font newFont = font;
        if (subsetString != null) {
            Subsetter subsetter = new RenumberingSubsetter(newFont, fontFactory);
            subsetter.setCMaps(cmapIds, 1);
            List<Integer> glyphs = GlyphCoverage.getGlyphCoverage(font, subsetString);
            subsetter.setGlyphs(glyphs);
            Set<Integer> removeTables = setRemoveTables();
            subsetter.setRemoveTables(removeTables);
            newFont = subsetter.subset().build();
        }
        if (strip) {
            Subsetter hintStripper = new HintStripper(newFont, fontFactory);
            Set<Integer> removeTables = setStripRemoveTables();
            hintStripper.setRemoveTables(removeTables);
            newFont = hintStripper.subset().build();
        }

        FileOutputStream fos = new FileOutputStream(outputFile);
        if (woff) {
            WritableFontData woffData = new WoffWriter().convert(newFont);
            woffData.copyTo(fos);
        } else if (eot) {
            WritableFontData eotData = new EOTWriter(mtx).convert(newFont);
            eotData.copyTo(fos);
        } else {
            fontFactory.serializeFont(newFont, fos);
        }
    }

    private Set<Integer> setStripRemoveTables() {
        Set<Integer> removeTables = new HashSet<Integer>();
        removeTables.add(Tag.fpgm);
        removeTables.add(Tag.prep);
        removeTables.add(Tag.cvt);
        removeTables.add(Tag.hdmx);
        removeTables.add(Tag.VDMX);
        removeTables.add(Tag.LTSH);
        removeTables.add(Tag.DSIG);
        removeTables.add(Tag.vhea);
        return removeTables;
    }

    private Set<Integer> setRemoveTables() {
        Set<Integer> removeTables = new HashSet<Integer>();
        // Most of the following are valid tables, but we don't renumber them yet, so strip
        removeTables.add(Tag.GDEF);
        removeTables.add(Tag.GPOS);
        removeTables.add(Tag.GSUB);
        removeTables.add(Tag.kern);
        removeTables.add(Tag.hdmx);
        removeTables.add(Tag.vmtx);
        removeTables.add(Tag.VDMX);
        removeTables.add(Tag.LTSH);
        removeTables.add(Tag.DSIG);
        removeTables.add(Tag.vhea);
        // AAT tables, not yet defined in sfntly Tag class
        removeTables.add(Tag.intValue(new byte[]{'m', 'o', 'r', 't'}));
        removeTables.add(Tag.intValue(new byte[]{'m', 'o', 'r', 'x'}));
        return removeTables;
    }

}
