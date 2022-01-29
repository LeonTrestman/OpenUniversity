public  interface Reduceable <T>{
    public T reduce() throws NonReduceable;
}
