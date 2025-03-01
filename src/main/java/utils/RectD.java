package utils;


public class RectD  {
    public double minX;
    public double minY;
    public double maxX;
    public double maxY;
    public boolean empty = true;

    public RectD() {
    }

    public RectD(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.empty = false;
    }

    public RectD(RectD r) {
        if (r == null) {
            this.minX = this.minY = this.maxX = this.maxY = 0.0F;
        } else {
            this.minX = r.minX;
            this.minY = r.minY;
            this.maxX = r.maxX;
            this.maxY = r.maxY;
        }
        this.empty = r.empty;
    }
    

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            RectD r = (RectD)o;
            return this.minX == r.minX && this.minY == r.minY && this.maxX == r.maxX && this.maxY == r.maxY;
        } else {
            return false;
        }
    }

    public int hashCode() {
        long result = this.minX != 0.0F ? Double.doubleToLongBits(this.minX) : 0;
        result = 31 * result + (this.minY != 0.0F ? Double.doubleToLongBits(this.minY) : 0);
        result = 31 * result + (this.maxX != 0.0F ? Double.doubleToLongBits(this.maxX) : 0);
        result = 31 * result + (this.maxY != 0.0F ? Double.doubleToLongBits(this.maxY) : 0);
        return (int)result;
    }

    public String toString() {
        return "RectD(" + this.minX + ", " + this.minY + ", " + this.maxX + ", " + this.maxY + ")";
    }

    public String toShortString() {
        return this.toShortString(new StringBuilder(32));
    }

    public String toShortString(StringBuilder sb) {
        sb.setLength(0);
        sb.append('[');
        sb.append(this.minX);
        sb.append(',');
        sb.append(this.minY);
        sb.append("][");
        sb.append(this.maxX);
        sb.append(',');
        sb.append(this.maxY);
        sb.append(']');
        return sb.toString();
    }


    public final boolean isEmpty() {
        return this.empty;
    }

    public final double width() {
        return this.maxX - this.minX;
    }

    public final double height() {
        return this.maxY - this.minY;
    }

    public final double centerX() {
        return (this.minX + this.maxX) * 0.5F;
    }

    public final double centerY() {
        return (this.minY + this.maxY) * 0.5F;
    }

    public double getMinX() {
        return this.minX;
    }
    public double getMaxX() {
        return this.maxX;
    }
    public double getMinY() {
        return this.minY;
    }
    public double getMaxY() {
        return this.maxY;
    }

    public void setEmpty() {
        this.minX = this.maxX = this.minY = this.maxY = 0.0F;
        this.empty = true;
    }

    public void set(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.empty = false;
    }

    public void set(RectD src) {
        this.minX = src.minX;
        this.minY = src.minY;
        this.maxX = src.maxX;
        this.maxY = src.maxY;
        this.empty = false;
    }

    public void set(RectF src) {
        this.minX = (double)src.left;
        this.minY = (double)src.top;
        this.maxX = (double)src.right;
        this.maxY = (double)src.bottom;
        this.empty = false;
    }

    public void offset(double dx, double dy) {
        this.minX += dx;
        this.minY += dy;
        this.maxX += dx;
        this.maxY += dy;
    }

    public void offsetTo(double newLeft, double newTop) {
        this.maxX += newLeft - this.minX;
        this.maxY += newTop - this.minY;
        this.minX = newLeft;
        this.minY = newTop;
    }

    public void inset(double dx, double dy) {
        this.minX += dx;
        this.minY += dy;
        this.maxX -= dx;
        this.maxY -= dy;
    }

    public boolean contains(double x, double y) {
        return !empty && this.minX < this.maxX && this.minY < this.maxY && x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY;
    }

    public boolean contains(double minX, double minY, double maxX, double maxY) {
        return !empty && this.minX < this.maxX && this.minY < this.maxY && this.minX <= minX && this.minY <= minY && this.maxX >= maxX && this.maxY >= maxY;
    }

    public boolean contains(RectD r) {
        return  !empty && this.minX < this.maxX && this.minY < this.maxY && this.minX <= r.minX && this.minY <= r.minY && this.maxX >= r.maxX && this.maxY >= r.maxY;
    }

    public boolean intersect(double minX, double minY, double maxX, double maxY) {
        if (this.minX < maxX && minX < this.maxX && this.minY < maxY && minY < this.maxY) {
            if (this.minX < minX) {
                this.minX = minX;
            }

            if (this.minY < minY) {
                this.minY = minY;
            }

            if (this.maxX > maxX) {
                this.maxX = maxX;
            }

            if (this.maxY > maxY) {
                this.maxY = maxY;
            }

            return !empty;
        } else {
            return false;
        }
    }

    public boolean intersect(RectD r) {
        return this.intersect(r.minX, r.minY, r.maxX, r.maxY);
    }

    public boolean setIntersect(RectD a, RectD b) {
        if (a.minX < b.maxX && b.minX < a.maxX && a.minY < b.maxY && b.minY < a.maxY) {
            this.minX = Math.max(a.minX, b.minX);
            this.minY = Math.max(a.minY, b.minY);
            this.maxX = Math.min(a.maxX, b.maxX);
            this.maxY = Math.min(a.maxY, b.maxY);
            empty = a.empty || b.empty;
            return true;
        } else {
            return false;
        }
    }

    public boolean intersects(double minX, double minY, double maxX, double maxY) {
        return !empty && this.minX < maxX && minX < this.maxX && this.minY < maxY && minY < this.maxY;
    }

    public static boolean intersects(RectD a, RectD b) {
        if (a.empty && b.empty) return true;
        if (a.empty != b.empty) return false;
        return  a.minX < b.maxX && b.minX < a.maxX && a.minY < b.maxY && b.minY < a.maxY;
    }

    public void roundOut(RectF dst) {
        dst.set((int)Math.floor((double)this.minX), (int)Math.floor((double)this.minY), (int)Math.ceil((double)this.maxX), (int)Math.ceil((double)this.maxY));
    }

    public void union(double minX, double minY, double maxX, double maxY) {
        if (empty) {
            set(minX, minY, maxX, maxY);
            return;
        }
        if (minX < maxX && minY < maxY) {
            if (this.minX < this.maxX && this.minY < this.maxY) {
                if (this.minX > minX) {
                    this.minX = minX;
                }

                if (this.minY > minY) {
                    this.minY = minY;
                }

                if (this.maxX < maxX) {
                    this.maxX = maxX;
                }

                if (this.maxY < maxY) {
                    this.maxY = maxY;
                }
            } else {
                this.minX = minX;
                this.minY = minY;
                this.maxX = maxX;
                this.maxY = maxY;
            }
        }

    }

    public void union(RectD r) {
        this.union(r.minX, r.minY, r.maxX, r.maxY);
    }

    public void union(double x, double y) {
        if (empty) {
            empty = false;
            this.minX = this.maxX = x;
            this.minY = this.maxY = y;
            return;
        }
        if (x < this.minX) {
            this.minX = x;
        } else if (x > this.maxX) {
            this.maxX = x;
        }

        if (y < this.minY) {
            this.minY = y;
        } else if (y > this.maxY) {
            this.maxY = y;
        }

    }

    public void sort() {
        double temp;
        if (this.minX > this.maxX) {
            temp = this.minX;
            this.minX = this.maxX;
            this.maxX = temp;
        }

        if (this.minY > this.maxY) {
            temp = this.minY;
            this.minY = this.maxY;
            this.maxY = temp;
        }

    }

    public int describeContents() {
        return 0;
    }

    public void scale(double scale) {
        if (scale != 1.0F) {
            this.minX *= scale;
            this.minY *= scale;
            this.maxX *= scale;
            this.maxY *= scale;
        }
    }
}
