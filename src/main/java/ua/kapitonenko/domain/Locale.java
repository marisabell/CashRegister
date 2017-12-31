package ua.kapitonenko.domain;

public class Locale extends BaseEntity implements Comparable<Locale>{
	private String name;
	private String language;
	
	public Locale() {
	}
	
	public Locale(Long id, String name, String language) {
		super(id);
		setName(name);
		this.language = language;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.language = name.substring(0, 2);
	}
	
	public String getLanguage() {
		return language;
	}
	
	@Override
	public int compareTo(Locale that) {
		return this.language.compareTo(that.language);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Locale{")
					   .append("id=").append(getId())
				       .append(", name=").append(name)
				       .append(", language=").append(language)
				       .append("}")
				       .toString();
	}
}
