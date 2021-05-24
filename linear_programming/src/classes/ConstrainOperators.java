package classes;

public enum ConstrainOperators {
    E{
        @Override
        public String toString() {
            return "=";
        }
    },
    LE{
        @Override
        public String toString() {
            return "<=";
        }
    }
    ,GE{
        @Override
        public String toString() {
            return ">=";
        }
    }
    ,LESS{
        @Override
        public String toString() {
            return "<";
        }
    }
    ,GREATER{
        @Override
        public String toString() {
            return ">";
        }
    }
}
