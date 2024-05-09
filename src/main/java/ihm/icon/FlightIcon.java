package ihm.icon;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
//-- Import Java
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class FlightIcon extends Graphics2D {

    @Override
    public void addRenderingHints(Map<?, ?> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRenderingHints'");
    }

    @Override
    public void clip(Shape arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clip'");
    }

    @Override
    public void draw(Shape arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    @Override
    public void drawGlyphVector(GlyphVector arg0, float arg1, float arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawGlyphVector'");
    }

    @Override
    public boolean drawImage(Image arg0, AffineTransform arg1, ImageObserver arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public void drawImage(BufferedImage arg0, BufferedImageOp arg1, int arg2, int arg3) {
        arg0.setRGB(255, 0, 0);

        arg0.createGraphics();
    }

    @Override
    public void drawRenderableImage(RenderableImage arg0, AffineTransform arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawRenderableImage'");
    }

    @Override
    public void drawRenderedImage(RenderedImage arg0, AffineTransform arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawRenderedImage'");
    }

    @Override
    public void drawString(String arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawString'");
    }

    @Override
    public void drawString(String arg0, float arg1, float arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawString'");
    }

    @Override
    public void drawString(AttributedCharacterIterator arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawString'");
    }

    @Override
    public void drawString(AttributedCharacterIterator arg0, float arg1, float arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawString'");
    }

    @Override
    public void fill(Shape arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fill'");
    }

    @Override
    public Color getBackground() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBackground'");
    }

    @Override
    public Composite getComposite() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComposite'");
    }

    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeviceConfiguration'");
    }

    @Override
    public FontRenderContext getFontRenderContext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFontRenderContext'");
    }

    @Override
    public Paint getPaint() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaint'");
    }

    @Override
    public Object getRenderingHint(Key arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRenderingHint'");
    }

    @Override
    public RenderingHints getRenderingHints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRenderingHints'");
    }

    @Override
    public Stroke getStroke() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStroke'");
    }

    @Override
    public AffineTransform getTransform() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransform'");
    }

    @Override
    public boolean hit(Rectangle arg0, Shape arg1, boolean arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hit'");
    }

    @Override
    public void rotate(double arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotate'");
    }

    @Override
    public void rotate(double arg0, double arg1, double arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rotate'");
    }

    @Override
    public void scale(double arg0, double arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scale'");
    }

    @Override
    public void setBackground(Color arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBackground'");
    }

    @Override
    public void setComposite(Composite arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setComposite'");
    }

    @Override
    public void setPaint(Paint arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPaint'");
    }

    @Override
    public void setRenderingHint(Key arg0, Object arg1) {
                
    }

    @Override
    public void setRenderingHints(Map<?, ?> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRenderingHints'");
    }

    @Override
    public void setStroke(Stroke arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStroke'");
    }

    @Override
    public void setTransform(AffineTransform arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTransform'");
    }

    @Override
    public void shear(double arg0, double arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shear'");
    }

    @Override
    public void transform(AffineTransform arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transform'");
    }

    @Override
    public void translate(int arg0, int arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'translate'");
    }

    @Override
    public void translate(double arg0, double arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'translate'");
    }

    @Override
    public void clearRect(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearRect'");
    }

    @Override
    public void clipRect(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clipRect'");
    }

    @Override
    public void copyArea(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copyArea'");
    }

    @Override
    public Graphics create() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

    @Override
    public void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawArc'");
    }

    @Override
    public boolean drawImage(Image arg0, int arg1, int arg2, ImageObserver arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public boolean drawImage(Image arg0, int arg1, int arg2, Color arg3, ImageObserver arg4) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, ImageObserver arg5) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, Color arg5, ImageObserver arg6) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8,
            ImageObserver arg9) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8,
            Color arg9, ImageObserver arg10) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawImage'");
    }

    @Override
    public void drawLine(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawLine'");
    }

    @Override
    public void drawOval(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawOval'");
    }

    @Override
    public void drawPolygon(int[] arg0, int[] arg1, int arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawPolygon'");
    }

    @Override
    public void drawPolyline(int[] arg0, int[] arg1, int arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawPolyline'");
    }

    @Override
    public void drawRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawRoundRect'");
    }

    @Override
    public void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillArc'");
    }

    @Override
    public void fillOval(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillOval'");
    }

    @Override
    public void fillPolygon(int[] arg0, int[] arg1, int arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillPolygon'");
    }

    @Override
    public void fillRect(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillRect'");
    }

    @Override
    public void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillRoundRect'");
    }

    @Override
    public Shape getClip() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClip'");
    }

    @Override
    public Rectangle getClipBounds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClipBounds'");
    }

    @Override
    public Color getColor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColor'");
    }

    @Override
    public Font getFont() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFont'");
    }

    @Override
    public FontMetrics getFontMetrics(Font arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFontMetrics'");
    }

    @Override
    public void setClip(Shape arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClip'");
    }

    @Override
    public void setClip(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClip'");
    }

    @Override
    public void setColor(Color arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setColor'");
    }

    @Override
    public void setFont(Font arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFont'");
    }

    @Override
    public void setPaintMode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPaintMode'");
    }

    @Override
    public void setXORMode(Color arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setXORMode'");
    }
    
}
