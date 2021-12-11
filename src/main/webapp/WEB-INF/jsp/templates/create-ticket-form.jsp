<form>
    <header>
        <h3>Create new ticket</h3>
    </header>
    <div class="form-group">
        <label for="service">Service</label>
        <select id="service" class="form-control" required>
            <option value="DNS">DNS</option>
            <option value="Email" selected>Email</option>
            <option value="Web">Web</option>
        </select>
    </div>
    <div class="form-group">
        <label for="subject">Subject</label>
        <input id="subject" placeholder="Short description of ticket" class="form-control" required>
    </div>
    <div class="form-group">
        <label for="description">Description</label>
        <textarea id="description" placeholder="Describe the problem in great detail!" class="form-control" required  maxlength="1000" rows="10"></textarea>
    </div>
    <button type="submit">Submit ticket</button>
</form>